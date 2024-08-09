package cabal.packet.builder;


import cabal.packet.Packet;
import cabal.types.UInt16;
import cabal.types.UInt32;
import cabal.types.UInt64;
import cabal.types.UInt8;

public interface PacketBuilder {
    Packet build();
    PacketBuilder putByte(byte value);
    PacketBuilder putByteArray(byte values[]);
    PacketBuilder put(SerializableNetworkObject value);
    PacketBuilder put(UInt8 value);
    PacketBuilder put(UInt16 value);
    PacketBuilder put(UInt32 value);
    PacketBuilder putShortLE(short value);
    PacketBuilder putIntLE(int value);
    PacketBuilder putUInt64LE(UInt64 value);
    PacketBuilder putUInt32LE(UInt32 value);
    PacketBuilder putUInt16LE(UInt16 value);
    PacketBuilder putUInt8LE(UInt8 value);
    PacketBuilder putInt64(long value);
    @Deprecated
    PacketBuilder putInt64LE(long value);
    PacketBuilder putString(String value);
    
}
