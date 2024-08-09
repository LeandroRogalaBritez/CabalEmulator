package cabal.packet.payload.server;

import cabal.packet.Packet;
import cabal.packet.builder.PacketBuilder;
import cabal.packet.builder.PacketBuilderFactory;
import cabal.packet.builder.ServerPacket;
import cabal.packet.handler.AuthHandler;
import cabal.types.UInt32;
import cabal.types.UInt64;
import cabal.types.UInt8;

public class S2SDisconnectTimer implements ServerPacket {
    public UInt32 timeout;
    public UInt8 unknown1;

    public S2SDisconnectTimer() {
    }

    public S2SDisconnectTimer(UInt32 timeout, UInt8 unknown1) {
        this.unknown1 = unknown1;
        this.timeout = timeout;
    }

    @Override
    public Packet generate(PacketBuilderFactory packetBuilderFactory) {
        PacketBuilder packet = packetBuilderFactory.create(AuthHandler.S2C_DISCONECT_TIMER);

        packet.putUInt32LE(timeout);
        packet.putUInt8LE(unknown1);

        return packet.build();
    }
}