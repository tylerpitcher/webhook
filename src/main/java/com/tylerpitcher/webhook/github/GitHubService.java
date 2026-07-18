package com.tylerpitcher.webhook.github;

import com.tylerpitcher.webhook.signature.verifier.SignatureVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Service
@RequiredArgsConstructor
class GitHubService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final SignatureVerifier verifier;

    void webhook(String event, String deliveryId, String signature, byte[] body) {
        log.info("Event: {}", event);
        log.info("Delivery Id: {}", deliveryId);
        log.info("Signature: {}", signature);

        byte[] bytes = GitHubSignatureExtractor.extract(signature);
        if (verifier.isNotValid(bytes, body)) {
            throw new InvalidGitHubSignatureException("Signature does not match expected");
        }

        JsonNode json = OBJECT_MAPPER.readTree(body);
        log.info("Body: {}", json);
    }
}
