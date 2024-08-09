package cabal.packet.handler;

import cabal.packet.Packet;
import cabal.packet.handler.chain.Chain;

public final class NullHandler implements Chain<Boolean, Packet> {
    
    private final static NullHandler INSTANCE = new NullHandler();        

    private NullHandler() {

    }
    
    public static NullHandler getInstance(){
        return INSTANCE;
    }
    
    @Override
    public Boolean handle(Packet value) {
        System.out.println("Cannot handle packet {" + String.format("%04X", value.getOpcode()) + "} yeat, data: {" + value.toByteString() + "}");
        return true;
    }

    @Override
    public void setNext(Chain<Boolean, Packet> chainElement) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public int compareTo(Chain<Boolean, Packet> t) {
        throw new UnsupportedOperationException("Not supported.");
    }        
}
