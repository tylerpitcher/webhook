package com.tylerpitcher.webhook.github;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidGitHubSignatureException extends RuntimeException {
    public InvalidGitHubSignatureException(String message) {
        super(message);
    }
}
