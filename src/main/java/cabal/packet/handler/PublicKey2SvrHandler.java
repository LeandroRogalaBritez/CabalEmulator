package cabal.packet.handler;

import cabal.AuthState;
import cabal.ClientSession;
import cabal.packet.Packet;
import cabal.packet.handler.chain.AbstractAuthPacketHandler;
import cabal.packet.payload.server.S2CPublicKey;
import cabal.types.UInt16;
import cabal.types.UInt8;

final class PublicKey2SvrHandler extends AbstractAuthPacketHandler {

    public PublicKey2SvrHandler() {
        super(AuthHandler.CSC_PUBLIC_KEY, AuthState.CAPTCHA_VERIFIED);
    }
              
    @Override
    protected Boolean handleValue(Packet packet) {
        ClientSession session = getCurrentSession();
        S2CPublicKey response = new S2CPublicKey();
        byte[] key = session.getRSA().getPublicKey();
        response.unknown1 = new UInt8((short) 1);
        response.publicKeyLength = new UInt16(key.length);
        response.payload = key;
        session.setState(AuthState.PUBLICKEY_INITIALIZED);

        session.sendPacket(response);
        return true;
    }
    
}
