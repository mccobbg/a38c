package com.a38c.eazybank.constants;

public class SecurityConstants {
    // A production release should never store the key here
    public static final String JWT_KEY = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";
    public static final String JWT_HEADER = "Authorization";
    public static final String ISSUER = "A38C";
    public static final int ITERATIONS = 6;
    public static final int MEM_LIMIT = 64 * 1024;
    public static final int HASH_LENGTH  = 32;
    public static final int SALT_LENGTH = 16;
    public static final int PARALLELISM = 1;
}
