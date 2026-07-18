package com.tylerpitcher.webhook.signature.verifier;

import com.tylerpitcher.webhook.signature.signer.SignatureSigner;

import java.security.MessageDigest;
import java.util.Objects;

final class HmacSHA256Verifier implements SignatureVerifier {

    private final SignatureSigner signer;

    HmacSHA256Verifier(byte[] secret) {
        Objects.requireNonNull(secret, "secret");
        signer = SignatureSigner.withHmacSHA256(secret);
    }

    @Override
    public boolean isValid(byte[] signature, byte[] payload) {
        Objects.requireNonNull(signature, "signature");
        Objects.requireNonNull(payload, "payload");

        byte[] expected = signer.sign(payload);

        return MessageDigest.isEqual(expected, signature);
    }
}
