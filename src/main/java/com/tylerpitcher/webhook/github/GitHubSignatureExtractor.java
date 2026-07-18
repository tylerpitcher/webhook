package com.tylerpitcher.webhook.github;

import java.util.HexFormat;

final class GitHubSignatureExtractor {

    private static final String PREFIX = "sha256=";

    private static final int PREFIX_LENGTH = PREFIX.length();

    public static byte[] extract(String header) {
        if (!header.startsWith(PREFIX)) {
            throw new InvalidGitHubSignatureException("Unsupported signature");
        }

        return HexFormat.of().parseHex(header.substring(PREFIX_LENGTH));
    }
}
