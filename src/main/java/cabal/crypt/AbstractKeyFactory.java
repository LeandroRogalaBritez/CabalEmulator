package cabal.crypt;

public abstract class AbstractKeyFactory implements KeyFactory {
    protected final KeyGenerator keyGenerator;

    public AbstractKeyFactory(KeyGeneratorFactory keyGeneratorFactory) {
        this.keyGenerator = keyGeneratorFactory.create();
    }
}
