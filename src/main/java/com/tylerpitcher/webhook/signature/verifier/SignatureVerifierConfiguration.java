package com.tylerpitcher.webhook.signature.verifier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Configuration
public class SignatureVerifierConfiguration {

    @Bean
    @ConditionalOnProperties({
            @ConditionalOnProperty(
                    name = "signature.algorithm",
                    havingValue = "HmacSHA256"),
            @ConditionalOnProperty(
                    name = "signature.secret")
    })
    public SignatureVerifier signatureVerifier(@Value("${signature.secret}") String secret) {
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        return SignatureVerifier.withHmacSHA256(bytes);
    }
}
