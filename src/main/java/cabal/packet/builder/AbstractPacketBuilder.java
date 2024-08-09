package cabal.packet.builder;

import cabal.types.*;

import java.io.ByteArrayOutputStream;

public abstract class AbstractPacketBuilder implements PacketBuilder {
    protected final ByteArrayOutputStream byteArrayOutputStream;

    public AbstractPacketBuilder() {
        this.byteArrayOutputStream = new ByteArrayOutputStream();
    }
    
    public AbstractPacketBuilder(int size){
        this.byteArrayOutputStream = new ByteArrayOutputStream(size);
    }
    
    @Override
    public PacketBuilder putByte(byte value) {
        byteArrayOutputStream.write(value);
        return this;
    }

    @Override
    public PacketBuilder putByteArray(byte[] values) {
        try{
            byteArrayOutputStream.write(values);
        } catch(Throwable t) {
            t.printStackTrace();
        }
        return this;
    }

    @Override
    public PacketBuilder put(SerializableNetworkObject value){
        if (value == null) {
            throw new IllegalArgumentException("Param value cannot be null.");
        }
        putByteArray(value.serialize());
        return this;
    }
    
    @Override
    public PacketBuilder putShortLE(short value) {
        value = ByteSwapper.swapBytes(value);
        
        return putByteArray(new byte[] {
            (byte)((value & 0xFF00) >> 8), 
            (byte)(value &0xFF)}
        );
    }
    
    @Override
    public PacketBuilder putIntLE(int value) {
        value = ByteSwapper.swapBytes(value);
        
        return putByteArray(new byte[]{
            (byte)((value & 0xFF000000) >> 24),
            (byte)((value & 0x00FF0000) >> 16),
            (byte)((value & 0x0000FF00) >> 8 ),
            (byte)((value & 0x000000FF))
        });
    }
    
    @Override
    public PacketBuilder putUInt32LE(UInt32 value) {
        return putByteArray(value.getBytesLE());
    }

    @Override
    public PacketBuilder putUInt64LE(UInt64 value) {
        return putByteArray(value.getBytesLE());
    }

    @Override
    public PacketBuilder putUInt16LE(UInt16 value) {
        return putByteArray(value.getBytesLE());
    }

    @Override
    public PacketBuilder putUInt8LE(UInt8 value) {
        return putByteArray(value.getBytesLE());
    }

    @Override
    public PacketBuilder putString(String value) {
        return putByteArray(value.getBytes());
    }

    @Override
    public PacketBuilder putInt64(long value) {                
        return putByteArray(new byte[]{            
            (byte)((value & 0x00000000000000FFl)),
            (byte)((value & 0x000000000000FF00l) >> 8 ),
            (byte)((value & 0x0000000000FF0000l) >> 16),
            (byte)((value & 0x00000000FF000000l) >> 24),
            (byte)((value & 0x000000FF00000000l) >> 32),
            (byte)((value & 0x0000FF0000000000l) >> 40),
            (byte)((value & 0x00FF000000000000l) >> 48),
            (byte)((value & 0xFF00000000000000l) >> 56)            
        });
    }
    
    @Override
    public PacketBuilder putInt64LE(long value) {
        return putByteArray(new byte[]{            
            (byte)((value & 0xFF00000000000000l) >> 56),
            (byte)((value & 0x00FF000000000000l) >> 48),
            (byte)((value & 0x0000FF0000000000l) >> 40),
            (byte)((value & 0x000000FF00000000l) >> 32),
            (byte)((value & 0x00000000FF000000l) >> 24),
            (byte)((value & 0x0000000000FF0000l) >> 16),
            (byte)((value & 0x000000000000FF00l) >> 8 ),
            (byte)((value & 0x00000000000000FFl))
        });
    }

    @Override
    public PacketBuilder put(UInt8 value) {
        return putByte(value.byteValue());
    }
    
    @Override
    public PacketBuilder put(UInt16 value) {
        return putByteArray(value.getBytesLE());
    }

    @Override
    public PacketBuilder put(UInt32 value) {
        return putByteArray(value.getBytesLE());
    }    
}