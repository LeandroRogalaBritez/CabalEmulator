package cabal.crypt;

public final class EP8KeyGeneratorFactory implements KeyGeneratorFactory{

    @Override
    public KeyGenerator create() {
        return new EP8KeyGenerator();
    }
    
}
