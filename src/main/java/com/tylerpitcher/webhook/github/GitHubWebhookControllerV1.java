package com.tylerpitcher.webhook.github;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/webhook/github")
@Tag(name = "GitHub Webhook", description = "GitHub webhook controller")
public class GitHubWebhookControllerV1 {

    private final GitHubService gitHubService;

    @Operation(
            summary = "Receive a secured GitHub webhook",
            description = "Receives webhook events from GitHub. After the signature is validated, the payload can be published.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Webhook accepted and processed successfully."),
                    @ApiResponse(
                            responseCode = "403",
                            description = "The supplied GitHub signature is invalid."),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Fatal error when processing webhook request.")})
    @PostMapping
    public ResponseEntity<Void> webhookSecure(
            @RequestHeader("X-GitHub-Event") String event,
            @RequestHeader("X-GitHub-Delivery") String deliveryId,
            @RequestHeader("X-Hub-Signature-256") String signature,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(type = "object", example = "{}")))
            @RequestBody byte[] body) {
        gitHubService.webhook(event, deliveryId, signature, body);
        return ResponseEntity.noContent().build();
    }
}
