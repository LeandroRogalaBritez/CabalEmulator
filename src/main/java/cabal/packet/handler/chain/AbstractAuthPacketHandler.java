package cabal.packet.handler.chain;

import cabal.AuthState;
import cabal.ClientSession;
import cabal.packet.Packet;

public abstract class AbstractAuthPacketHandler extends AbstractOpcodeHandler {
    protected final AuthState requiredState;

    public AbstractAuthPacketHandler(short opcodeHandled, AuthState requiredState) {
        super(opcodeHandled);
        this.requiredState = requiredState;
    }
    
    protected void onInvalidState(){
        System.out.println("Current client state {" + getCurrentSession().getState() + "} required state {"+  requiredState + "}, ignoring request.");
    }
    
    @Override
    protected boolean canHandle(Packet value) {
        if (super.canHandle(value)) {
            if (getCurrentSession().getState() == requiredState) {
                return true;
            } else {
                onInvalidState();
            }
        }
        return false;
    }        
        
    protected ClientSession getCurrentSession(){
        return ClientSession.getCurrentSession();
    }
    
}
