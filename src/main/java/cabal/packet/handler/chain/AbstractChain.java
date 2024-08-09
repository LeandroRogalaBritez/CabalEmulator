package cabal.packet.handler.chain;

public abstract class AbstractChain<ReturnType, ParameterType> implements Chain<ReturnType, ParameterType> {
    protected Chain<ReturnType, ParameterType> next;
    public AbstractChain() {
        this.next = null;
    }
    protected abstract boolean canHandle(ParameterType value);
    protected abstract ReturnType handleValue(ParameterType value);
    protected abstract ReturnType endOfChain();
    
    @Override
    public ReturnType handle(ParameterType value) {
        if (canHandle(value)) {
            return handleValue(value);
        } else {
            if (next == null) {
                return endOfChain();
            } else {
                return next.handle(value);
            }
        }
    }
    
    @Override
    public void setNext(Chain<ReturnType, ParameterType> next) {
        this.next = next;
    }
}
