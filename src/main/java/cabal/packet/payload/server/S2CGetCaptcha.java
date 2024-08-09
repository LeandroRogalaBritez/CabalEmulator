package cabal.packet.payload.server;

import cabal.packet.Packet;
import cabal.packet.builder.PacketBuilder;
import cabal.packet.builder.PacketBuilderFactory;
import cabal.packet.builder.ServerPacket;
import cabal.packet.handler.AuthHandler;
import cabal.types.UInt16;
import cabal.types.UInt32;
import cabal.types.UInt8;

public class S2CGetCaptcha implements ServerPacket {
    public UInt8 active;
    public UInt32 unkown1;
    public UInt32 timeout;
    public UInt16 captchaSize;
    public byte[] captcha = new byte[4097];

    public S2CGetCaptcha() {
    }

    public S2CGetCaptcha(UInt8 active, UInt32 unkown1, UInt32 timeout, UInt16 captchaSize) {
        this.active = active;
        this.unkown1 = unkown1;
        this.timeout = timeout;
        this.captchaSize = captchaSize;
    }
    
    @Override
    public Packet generate(PacketBuilderFactory packetBuilderFactory) {
        PacketBuilder packet = packetBuilderFactory.create(AuthHandler.CSC_GET_CAPTCHA);
        
        packet.putUInt8LE(active);
        packet.putUInt32LE(unkown1);
        packet.putUInt32LE(timeout);
        packet.putUInt16LE(captchaSize);
        packet.putByteArray(captcha);

        return packet.build();
    }    
}