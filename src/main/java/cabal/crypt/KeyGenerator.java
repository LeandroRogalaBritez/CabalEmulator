package cabal.crypt;

import cabal.types.UInt32;

public interface KeyGenerator {
    byte[] generateCommonKey();
    byte[] generateAuthKey(UInt32 rootKey);
}
