package cabal.packet.payload.client;

import cabal.packet.Packet;
import cabal.types.UInt8;

public class C2SVerifyCaptcha {
    public final String captcha;
    public final UInt8 unknown1;
    public final UInt8 unknown2;

    public C2SVerifyCaptcha(Packet packet) {
        this.captcha = packet.getString(6);
        this.unknown1 = new UInt8((short)0);
        this.unknown2 = new UInt8((short)0);
    }
}
