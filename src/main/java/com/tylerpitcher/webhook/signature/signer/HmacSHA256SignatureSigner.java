package com.tylerpitcher.webhook.signature.signer;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.util.Objects;

final class HmacSHA256SignatureSigner implements SignatureSigner {

    private static final String ALGORITHM = "HmacSHA256";

    private final SecretKeySpec key;

    HmacSHA256SignatureSigner(byte[] secret) {
        Objects.requireNonNull(secret, "secret");
        this.key = new SecretKeySpec(
                secret,
                ALGORITHM);
    }

    @Override
    public byte[] sign(byte[] payload) {
        Objects.requireNonNull(payload, "payload");
        Mac mac = createMac(key);
        return mac.doFinal(payload);
    }

    private static Mac createMac(SecretKeySpec key) {
        try {
            Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(key);
            return mac;
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Unable to initialize HMAC-SHA256", e);
        }
    }
}
