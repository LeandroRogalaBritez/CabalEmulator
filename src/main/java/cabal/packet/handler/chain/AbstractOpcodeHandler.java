package cabal.packet.handler.chain;

import cabal.packet.Packet;

public abstract class AbstractOpcodeHandler extends AbstractChain<Boolean, Packet> {
    protected short opcodeHandled;

    public AbstractOpcodeHandler(short opcodeHandled) {
        this.opcodeHandled = opcodeHandled;
    }
    
    @Override
    protected boolean canHandle(Packet value) {
        if (value == null) {
            return false;
        }
        return value.getOpcode() == opcodeHandled;
    }    

    @Override
    protected Boolean endOfChain() {
        return Boolean.FALSE;
    }

    @Override
    public int compareTo(Chain<Boolean, Packet> t) {
        return Short.valueOf(opcodeHandled).compareTo(((AbstractOpcodeHandler)t).opcodeHandled);
    }
    
}
