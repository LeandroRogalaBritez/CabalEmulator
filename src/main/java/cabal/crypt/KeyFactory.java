package cabal.crypt;

import cabal.types.UInt32;

public interface KeyFactory {
    Key create();
    Key create(UInt32 authKey);
}
