package cabal.packet.payload.server;

import cabal.packet.Packet;
import cabal.packet.builder.PacketBuilder;
import cabal.packet.builder.PacketBuilderFactory;
import cabal.packet.builder.ServerPacket;
import cabal.packet.handler.AuthHandler;
import cabal.types.UInt32;
import cabal.types.UInt8;

public class S2SWarEntryStatus implements ServerPacket {
    public UInt8 unknown1;
    public UInt8 unknown2;
    public UInt8 unknown3;
    public UInt8 unknown4;
    public UInt32 receivedWarReward;

    public S2SWarEntryStatus() {
    }

    public S2SWarEntryStatus(UInt8 unknown1, UInt8 unknown2, UInt8 unknown3, UInt8 unknown4, UInt32 receivedWarReward) {
        this.unknown1 = unknown1;
        this.unknown2 = unknown2;
        this.unknown3 = unknown3;
        this.unknown4 = unknown4;
        this.receivedWarReward = receivedWarReward;
    }

    @Override
    public Packet generate(PacketBuilderFactory packetBuilderFactory) {
        PacketBuilder packet = packetBuilderFactory.create(AuthHandler.CSC_WAR_ENTRY_STATUS);

        packet.putUInt8LE(unknown1);
        packet.putUInt8LE(unknown2);
        packet.putUInt8LE(unknown3);
        packet.putUInt8LE(unknown4);
        packet.putUInt32LE(receivedWarReward);

        return packet.build();
    }
}