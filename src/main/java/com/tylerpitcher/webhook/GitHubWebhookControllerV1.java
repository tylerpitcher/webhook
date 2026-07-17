package com.tylerpitcher.webhook;

import com.tylerpitcher.webhook.signature.verifier.SignatureVerifier;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/webhook/github")
@Tag(name = "GitHub Webhook", description = "GitHub webhook controller")
public class GitHubWebhookControllerV1 {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final SignatureVerifier signatureVerifier;

    @Operation(
            summary = "Receive an insecure GitHub webhook",
            description = "Receives webhook events from GitHub."
    )
    @PostMapping("/insecure")
    public ResponseEntity<Void> webhookInsecure(
            @RequestHeader("X-Hub-Signature-256") String signature,
            @RequestHeader("X-GitHub-Event") String event,
            @RequestHeader("X-GitHub-Delivery") String deliveryId,
            @RequestBody JsonNode body) {
        log.info("Signature: {}", signature);
        log.info("Event: {}", event);
        log.info("Delivery Id: {}", deliveryId);
        log.info("Body: {}", body);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Receive a secured GitHub webhook",
            description = "Receives webhook events from GitHub. After the signature is validated, the payload can be published."
    )
    @PostMapping("/secure")
    public ResponseEntity<Void> webhookSecure(
            @RequestHeader("X-Hub-Signature-256") String signature,
            @RequestHeader("X-GitHub-Event") String event,
            @RequestHeader("X-GitHub-Delivery") String deliveryId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    type = "object",
                                    example = """
                                    {
                                      "ref": "refs/heads/main",
                                      "repository": {
                                        "full_name": "octocat/Hello-World"
                                      },
                                      "pusher": {
                                        "name": "octocat"
                                      }
                                    }
                                    """)))
            @RequestBody byte[] body) {
        log.info("Signature: {}", signature);
        log.info("Event: {}", event);
        log.info("Delivery Id: {}", deliveryId);

        byte[] bytes = GitHubSignatureParser.parse(signature);
        if (!signatureVerifier.verify(bytes, body)) {
            throw new InvalidWebhookSignatureException("Signature does not match expected");
        }

        JsonNode json = OBJECT_MAPPER.readTree(body);
        log.info("Body: {}", json);

        return ResponseEntity.noContent().build();
    }
}
