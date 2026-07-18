package com.tylerpitcher.webhook.signature.verifier;

@FunctionalInterface
public interface SignatureVerifier {

    boolean isValid(byte[] signature, byte[] payload);

    default boolean isNotValid(byte[] signature, byte[] payload) {
        return isValid(signature, payload);
    }

    static SignatureVerifier withHmacSHA256(byte[] secret) {
        return new HmacSHA256Verifier(secret);
    }
}
