package cabal.core;

import cabal.types.ByteSwapper;
import cabal.types.UInt16;
import cabal.types.UInt32;
import cabal.types.UInt8;

public final class Serializer {

    private Serializer() {
        throw new AssertionError();
    }
    
    public static byte[] serializeInt16BE(short value){
        return new byte[]{ 
            (byte)((value & 0xFF00) >> 8), 
            (byte)(value &0xFF)
        };
    }
    
    public static byte[] serializeInt16LE(short value){
        return serializeInt16BE(ByteSwapper.swapBytes(value));
    }
    
    public static byte[] serializeInt32BE(int value) {
        return new byte[]{
            (byte)((value & 0xFF000000) >> 24),
            (byte)((value & 0x00FF0000) >> 16),
            (byte)((value & 0x0000FF00) >> 8 ),
            (byte)((value & 0x000000FF))
        };        
    }
    
    public static byte[] serializeInt32LE(int value) {
        return serializeInt32BE(ByteSwapper.swapBytes(value));
    }                   
    
    public static byte[] serializeInt64BE(long value) {                
        return new byte[]{            
            (byte)((value & 0x00000000000000FFl)),
            (byte)((value & 0x000000000000FF00l) >> 8 ),
            (byte)((value & 0x0000000000FF0000l) >> 16),
            (byte)((value & 0x00000000FF000000l) >> 24),
            (byte)((value & 0x000000FF00000000l) >> 32),
            (byte)((value & 0x0000FF0000000000l) >> 40),
            (byte)((value & 0x00FF000000000000l) >> 48),
            (byte)((value & 0xFF00000000000000l) >> 56)            
        };
    }
        
    public static byte[] serializeInt64LE(long value) {
        return new byte[]{            
            (byte)((value & 0xFF00000000000000l) >> 56),
            (byte)((value & 0x00FF000000000000l) >> 48),
            (byte)((value & 0x0000FF0000000000l) >> 40),
            (byte)((value & 0x000000FF00000000l) >> 32),
            (byte)((value & 0x00000000FF000000l) >> 24),
            (byte)((value & 0x0000000000FF0000l) >> 16),
            (byte)((value & 0x000000000000FF00l) >> 8 ),
            (byte)((value & 0x00000000000000FFl))
        };
    }

    public static byte serializeUInt8LE(UInt8 value) {
        return value.byteValue();
    }
    
    public static byte[] serializeUInt16LE(UInt16 value) {
        return value.getBytesLE();
    }
    
    public static byte[] serializeUInt32LE(UInt32 value) {
        return value.getBytesLE();
    }                        
}
