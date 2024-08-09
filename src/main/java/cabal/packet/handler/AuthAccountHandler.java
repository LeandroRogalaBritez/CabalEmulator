package cabal.packet.handler;

import cabal.AuthState;
import cabal.ClientSession;
import cabal.packet.Packet;
import cabal.packet.handler.chain.AbstractAuthPacketHandler;
import cabal.packet.payload.server.*;
import cabal.types.UInt32;
import cabal.types.UInt8;

final class AuthAccountHandler extends AbstractAuthPacketHandler {
    public AuthAccountHandler() {
        super(AuthHandler.CSC_AUTHACCOUNT, AuthState.PUBLICKEY_INITIALIZED);
    }
   
    @Override
    protected Boolean handleValue(Packet packet) {
        ClientSession session = getCurrentSession();
        S2CAuthAccount result = new S2CAuthAccount();

        result.serverStatus = new UInt32(0);
        result.unknown1 = new UInt32(0);
        result.unknown2 = new UInt32(0);

        session.sendPacket(result);
        startDisconnectTimer(session);
        return Boolean.TRUE;
    }

    private void startDisconnectTimer(ClientSession session) {
        S2SDisconnectTimer response = new S2SDisconnectTimer();
        response.timeout = new UInt32(120000);
        response.unknown1 = new UInt8((short) 0);
        session.sendPacket(response);
    }

}
