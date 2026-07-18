package com.tylerpitcher.webhook.signature.verifier;

import com.tylerpitcher.webhook.signature.signer.SignatureSigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.Objects;

final class HmacSHA256Verifier implements SignatureVerifier {

    private static final Logger log = LoggerFactory.getLogger(HmacSHA256Verifier.class);

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
        log.info("Expected: {}", expected);
        log.info("Given: {}", signature);

        return MessageDigest.isEqual(expected, signature);
    }
}
