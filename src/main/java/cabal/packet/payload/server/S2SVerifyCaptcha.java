package cabal.packet.payload.server;

import cabal.packet.Packet;
import cabal.packet.builder.PacketBuilder;
import cabal.packet.builder.PacketBuilderFactory;
import cabal.packet.builder.ServerPacket;
import cabal.packet.handler.AuthHandler;
import cabal.types.UInt32;
import cabal.types.UInt8;

public class S2SVerifyCaptcha implements ServerPacket {
    public UInt8 sucess;
    public UInt32 unknown1;
    public UInt32 unknown2;

    public S2SVerifyCaptcha() {
    }

    public S2SVerifyCaptcha(UInt8 sucess, UInt32 unknown1, UInt32 unknown2) {
        this.sucess = sucess;
        this.unknown1 = unknown1;
        this.unknown2 = unknown2;
    }

    @Override
    public Packet generate(PacketBuilderFactory packetBuilderFactory) {
        PacketBuilder packet = packetBuilderFactory.create(AuthHandler.CSC_VERIFY_CAPTCHA);

        packet.putUInt8LE(sucess);
        packet.putUInt32LE(unknown1);
        packet.putUInt32LE(unknown2);

        return packet.build();
    }
}
