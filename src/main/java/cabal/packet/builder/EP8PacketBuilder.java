package cabal.packet.builder;

import cabal.packet.Header;
import cabal.packet.Packet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

final class EP8PacketBuilder extends AbstractPacketBuilder {
    final static short PACKET_SIGNATURE = (short)0xB7E2;
    private final short opcode;
    
    public EP8PacketBuilder(short opcode) {
        this.opcode = opcode;
        
        putShortLE(PACKET_SIGNATURE);
        putShortLE((short)0x0000);
        putShortLE(opcode);
    }
    
    @Override
    public Packet build() {
        byte raw[] = byteArrayOutputStream.toByteArray();
        
        ByteBuffer buff = ByteBuffer.wrap(raw);
        buff.order(ByteOrder.LITTLE_ENDIAN);
        buff.putShort(2, (short)raw.length);
        
        return new Packet(
            new Header(PACKET_SIGNATURE, (short)raw.length, raw),
            opcode, 
            buff
        );
    }
    
}
