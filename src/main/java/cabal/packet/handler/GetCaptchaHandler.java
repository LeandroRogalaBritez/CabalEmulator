package cabal.packet.handler;

import cabal.AuthState;
import cabal.ClientSession;
import cabal.packet.Packet;
import cabal.packet.handler.chain.AbstractAuthPacketHandler;
import cabal.packet.payload.server.S2CGetCaptcha;
import cabal.types.UInt16;
import cabal.types.UInt32;
import cabal.types.UInt8;

final class GetCaptchaHandler extends AbstractAuthPacketHandler {

    public GetCaptchaHandler() {
        super(AuthHandler.CSC_GET_CAPTCHA, AuthState.RIGHT_CLIENT_VERSION);
    }
              
    @Override
    protected Boolean handleValue(Packet packet) {
        ClientSession session = getCurrentSession();
        S2CGetCaptcha response = new S2CGetCaptcha(new UInt8((short)1), new UInt32(1),
                new UInt32(120000), new UInt16(session.getCaptchaReader().getDataLength()));
        System.arraycopy(session.getCaptchaReader().getData(), 0, response.captcha, 0, response.captchaSize.intValue());
        session.sendPacket(response);
        return true;
    }

    
}
