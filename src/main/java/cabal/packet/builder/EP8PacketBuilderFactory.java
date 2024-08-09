package cabal.packet.builder;

public class EP8PacketBuilderFactory implements PacketBuilderFactory {

    @Override
    public PacketBuilder create(short opcode) {
        return new EP8PacketBuilder(opcode);
    }
    
}
