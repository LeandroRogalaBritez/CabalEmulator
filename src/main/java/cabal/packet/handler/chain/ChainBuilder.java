package cabal.packet.handler.chain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChainBuilder<T extends Chain> {
    protected final List<T> chainList;
    protected boolean built;
    protected T lastElement = null;
    
    private ChainBuilder() {
        this.chainList = new ArrayList<T>();
        this.built = false;
    }
    
    public static ChainBuilder create(){
        return new ChainBuilder();
    }
    
    public ChainBuilder<T> add(T chainElement) {
        if (built) {
            throw new IllegalStateException("Chain already built !");
        }
        
        if (chainElement != null) {
            if (!chainList.contains(chainElement)) {
                chainList.add(chainElement);
            }
        }        
        return this;
    }

    public ChainBuilder<T> setLastElement(T lastElement) {
        this.lastElement = lastElement;
        return this;
    }
    
    public Chain build() throws IllegalStateException {
        if (!built) {
            if (chainList.isEmpty()) {
                throw new IllegalStateException("Chain list is empty !");
            }
            built = true;

            System.out.println("Building chain...");

            Collections.sort(chainList);
            T oldChainElement = null;
            
            for (T chainElement : chainList) {
                if (oldChainElement != null) {
                    oldChainElement.setNext(chainElement);
                }
                oldChainElement = chainElement;
                System.out.println("Adding chain element: {" + chainElement.getClass().getSimpleName() + "}");
            }
            
            oldChainElement.setNext(lastElement);

            System.out.println("Chain build...");

            return chainList.get(0);
        } else {
            throw new IllegalStateException("Chain already built !");
        }                
    }
}
