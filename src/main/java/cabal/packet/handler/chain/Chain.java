package cabal.packet.handler.chain;

public interface Chain<ReturnType, ParameterType> extends Comparable<Chain<ReturnType, ParameterType>> {
    ReturnType handle(ParameterType value);
    void setNext(Chain<ReturnType, ParameterType> chainElement);
}
