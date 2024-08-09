package cabal.crypt;

import cabal.types.UInt32;

public final class EP8KeyFactory extends AbstractKeyFactory {

    public EP8KeyFactory() {
        this(new EP8KeyGeneratorFactory());
    }
    
    public EP8KeyFactory(KeyGeneratorFactory keyGeneratorFactory) {
        super(keyGeneratorFactory);
    }
    
    @Override
    public Key create() {
        return new EP8Key(keyGenerator);
    }

    @Override
    public Key create(UInt32 authKey) {
        return new EP8Key(keyGenerator, authKey);
    }
    
}
