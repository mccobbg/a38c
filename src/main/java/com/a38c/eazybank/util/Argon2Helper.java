package com.a38c.eazybank.util;

import java.io.UnsupportedEncodingException;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

import java.security.SecureRandom;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Argon2Helper {

    private final int iterations = 3;
    private final int memLimit = 64 * 1024;
    private final int hashLength = 32;
    private final int saltLength = 16;
    private final int parallelism = 2;
    private final SecureRandom secureRandom = new SecureRandom();

    public String encode(String password) throws UnsupportedEncodingException {
        byte[] salt = generateSalt16Byte();
        Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
            .withVersion(Argon2Parameters.ARGON2_VERSION_13)
            .withIterations(iterations)
            .withMemoryAsKB(memLimit)
            .withParallelism(parallelism)
            .withSalt(salt);

        Argon2BytesGenerator generate = new Argon2BytesGenerator();
        generate.init(builder.build());

        byte[] hash = new byte[hashLength];
        generate.generateBytes(password.getBytes(StandardCharsets.UTF_8), hash, 0, hash.length);
        String b64Salt = base64Encoding(salt);
        String b64Hash = base64Encoding(hash);
        return b64Salt + "$" + b64Hash;
    }

    public boolean verify(String password, String encodedHash) throws Exception {

        String[] splitHash = encodedHash.split("$");
        byte[] salt = base64Decoding(splitHash[0]);
        byte[] hash = base64Decoding(splitHash[1]);

        Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
            .withVersion(Argon2Parameters.ARGON2_VERSION_13)
            .withIterations(iterations)
            .withMemoryAsKB(memLimit)
            .withParallelism(parallelism)
            .withSalt(salt);

        Argon2BytesGenerator generate = new Argon2BytesGenerator();
        generate.init(builder.build());
 
        byte[] hashToCompare = new byte[hashLength];
        generate.generateBytes(password.getBytes(StandardCharsets.UTF_8), hashToCompare, 0, hashLength);
        return compare(hash, hashToCompare);
    }

    private static boolean compare(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }
        return result == 0;
    }
    
    private byte[] generateSalt16Byte() {
        byte[] salt = new byte[saltLength];
        
        secureRandom.nextBytes(salt);
        return salt;
    }

    private String base64Encoding(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }

    private byte[] base64Decoding(String input) {
        return Base64.getDecoder().decode(input);
    }
}
