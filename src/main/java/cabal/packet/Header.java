package cabal.packet;

import java.util.Arrays;

public class Header {
    private final short signature;
    private final short size;   
    private final byte data[];
    
    public Header(short signature, short size, byte data[]) {
        this.signature = signature;
        this.size = size;
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }
    
    public short getSignature() {
        return signature;
    }

    public short getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Header{" +
                "signature=" + signature +
                ", size=" + size +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
