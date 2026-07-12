package com.tylerpitcher.webhook.signature.verifier;

@FunctionalInterface
public interface SignatureVerifier {

    boolean verify(byte[] signature, byte[] payload);

    static SignatureVerifier withHmacSHA256(byte[] secret) {
        return new HmacSHA256Verifier(secret);
    }
}
