package cabal.packet.handler;

import cabal.AuthState;
import cabal.ClientSession;
import cabal.packet.Packet;
import cabal.packet.builder.PacketBuilder;
import cabal.packet.handler.chain.AbstractAuthPacketHandler;
import cabal.packet.payload.client.C2SVerifyCaptcha;
import cabal.packet.payload.server.S2SVerifyCaptcha;
import cabal.types.UInt16;
import cabal.types.UInt32;
import cabal.types.UInt8;

final class VerifyCaptcha2SvrHandler extends AbstractAuthPacketHandler {

    public VerifyCaptcha2SvrHandler() {
        super(AuthHandler.CSC_VERIFY_CAPTCHA, AuthState.RIGHT_CLIENT_VERSION);
    }
              
    @Override
    protected Boolean handleValue(Packet packet) {
        ClientSession session = getCurrentSession();
        C2SVerifyCaptcha data = new C2SVerifyCaptcha(packet);
        S2SVerifyCaptcha response = new S2SVerifyCaptcha();
        if (session.getCaptchaReader().getName().equals(data.captcha.trim())) {
            response.sucess = new UInt8((short)1);
            session.setState(AuthState.CAPTCHA_VERIFIED);
        } else {
            response.sucess = new UInt8((short)0);
        }
        response.unknown1 = new UInt32(0);
        response.unknown2 = new UInt32(0);
        session.sendPacket(response);
        return true;
    }
    
}
