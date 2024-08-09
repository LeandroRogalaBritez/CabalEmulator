package cabal.packet.builder;

import cabal.packet.Packet;

public interface ServerPacket {
    Packet generate(PacketBuilderFactory packetBuilderFactory);
}
