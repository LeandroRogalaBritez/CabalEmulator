package cabal.packet.payload.server;

import cabal.packet.Packet;
import cabal.packet.builder.PacketBuilder;
import cabal.packet.builder.PacketBuilderFactory;
import cabal.packet.builder.ServerPacket;
import cabal.packet.handler.AuthHandler;
import cabal.types.UInt32;

public class S2CAuthAccount implements ServerPacket {
    public UInt32 serverStatus;
    public UInt32 unknown1;
    public UInt32 unknown2;

    public S2CAuthAccount() {
    }

    @Override
    public Packet generate(PacketBuilderFactory packetBuilderFactory) {
        PacketBuilder builder = packetBuilderFactory.create(AuthHandler.CSC_AUTHACCOUNT);
        
        builder.putUInt32LE(serverStatus);
        builder.putUInt32LE(unknown1);
        builder.putUInt32LE(unknown2);

        return builder.build();
    }
}