package cabal.packet.builder;

public interface PacketBuilderFactory {
    PacketBuilder create(short opcode);
}
