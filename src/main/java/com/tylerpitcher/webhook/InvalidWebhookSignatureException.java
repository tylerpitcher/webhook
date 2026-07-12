package com.tylerpitcher.webhook;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
class InvalidWebhookSignatureException extends RuntimeException {
  public InvalidWebhookSignatureException(String message) {
    super(message);
  }
}
