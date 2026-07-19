package com.tylerpitcher.webhook.github;

import java.util.HexFormat;

final class GitHubSignatureExtractor {

    private static final String SHA256_PREFIX = "sha256=";

    private static final int SHA256_PREFIX_LENGTH = SHA256_PREFIX.length();

    public static byte[] extract(String header) {
        if (!header.startsWith(SHA256_PREFIX)) {
            throw new InvalidGitHubSignatureException("Unsupported signature");
        }

        return HexFormat.of().parseHex(header.substring(SHA256_PREFIX_LENGTH));
    }
}
