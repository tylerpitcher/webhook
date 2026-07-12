package com.tylerpitcher.webhook.signature.signer;

@FunctionalInterface
public interface SignatureSigner {

    byte[] sign(byte[] payload);

    static SignatureSigner withHmacSHA256(byte[] secret) {
        return new HmacSHA256SignatureSigner(secret);
    }
}
