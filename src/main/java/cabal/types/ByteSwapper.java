package cabal.types;

public final class ByteSwapper {

    private ByteSwapper() {
        throw new AssertionError();
    }
    
    public static short swapBytes(short value){                
        return (short)(((value & 0xFF) << 8) | ((value & 0xFF00) >> 8));
    }
    
    public static int swapBytes(int value){
        return (int)(
                ((value & 0xFF000000) >> 24) |
                ((value & 0x00FF0000) >>  8) |
                ((value & 0x0000FF00) <<  8) |
                ((value & 0x000000FF) << 24)                 
        );
    }        
}
