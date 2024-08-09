package cabal.packet.handler;

import cabal.AuthState;
import cabal.ClientSession;
import cabal.packet.Packet;
import cabal.packet.builder.PacketBuilder;
import cabal.packet.handler.chain.AbstractAuthPacketHandler;
import cabal.packet.payload.client.C2SCheckVersion;
import cabal.packet.payload.server.S2CCheckVersion;
import cabal.types.UInt16;
import cabal.types.UInt32;

final class CheckVersionHandler extends AbstractAuthPacketHandler {
    private final static AuthState otherValidState = AuthState.AUTHED;
    private final static boolean CHECK_CLIENT_VERSION = false;
    private final int EXPECT_CLIENT_VERSION = 13182;
    private final UInt32 serverMagicKey = new UInt32(5834620);

    public CheckVersionHandler() {
        super(AuthHandler.CSC_CHECKVERSION, AuthState.HANDSHAKED);
    }

    @Override
    protected boolean canHandle(Packet value) {
        if (value.getOpcode() == opcodeHandled) {
            if (getCurrentSession().getState() == requiredState || getCurrentSession().getState() == otherValidState) {
                return true;
            } else {
                onInvalidState();
            }
        }
        return false;
    }
              
    @Override
    protected Boolean handleValue(Packet packet) {
        ClientSession session = getCurrentSession();
        C2SCheckVersion data = new C2SCheckVersion(packet);
        S2CCheckVersion response = new S2CCheckVersion();

        System.out.println("Client version: " + data.clientVersion);

        boolean success = false;

        if (CHECK_CLIENT_VERSION) {
            if (data.clientVersion.intValue() == EXPECT_CLIENT_VERSION) {
                success = true;
            } else {
                if (session.getState() == AuthState.AUTHED) {
                    session.setState(AuthState.SERVER_SELECTED);
                } else {
                    session.setState(AuthState.WRONG_CLIENT_VERSION);
                }

                System.out.println("Wrong client version {" + data.clientVersion + "}, closing connection !");

                response.clientVersion = new UInt32(EXPECT_CLIENT_VERSION);
                response.serverMagicKey = new UInt32(1);
                response.reserved = new UInt32(1);
            }
        } else {
            success = true;
        }

        if (success) {
            if (session.getState() == AuthState.AUTHED) {
                session.setState(AuthState.SERVER_SELECTED);
            } else {
                session.setState(AuthState.RIGHT_CLIENT_VERSION);
            }

            response.clientVersion = new UInt32(EXPECT_CLIENT_VERSION);
            response.serverMagicKey = serverMagicKey;
            response.reserved = new UInt32(0);

            System.out.println("Client version {" + data.clientVersion + "} accepted");
        }

        session.sendPacket(response);

        return true;
    }
    
}
