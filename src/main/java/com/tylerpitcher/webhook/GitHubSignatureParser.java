package com.tylerpitcher.webhook;

import java.util.HexFormat;

final class GitHubSignatureParser {

    private static final String PREFIX = "sha256=";

    public static byte[] parse(String header) {
        if (!header.startsWith(PREFIX)) {
            throw new IllegalArgumentException("Unsupported signature");
        }

        return HexFormat.of().parseHex(header.substring(PREFIX.length()));
    }
}
