package com.gamebuilder.web;

public final class InputSanitizer {

    private InputSanitizer() {
        // utility class
    }

    public static String sanitize(String input) {
        if (input == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(input.length());
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (isAllowed(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static boolean isAllowed(char c) {
        return (c >= 'A' && c <= 'Z')
                || (c >= 'a' && c <= 'z')
                || (c >= '0' && c <= '9')
                || c == '!'
                || c == '?'
                || c == '.'
                || c == ','
                || c == '_'
                || c == '-'
                || c == '+'
                || c == '='
                || c == '\n'
                || c == ' ';
    }
}
