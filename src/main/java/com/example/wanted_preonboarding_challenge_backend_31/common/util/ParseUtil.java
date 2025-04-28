package com.example.wanted_preonboarding_challenge_backend_31.common.util;

public class ParseUtil {

    public static String snakeToCamelCase(String input) {
        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;
        for (char c : input.toCharArray()) {
            if (c == '_') {
                nextUpper = true;
            } else {
                result.append(nextUpper ? Character.toUpperCase(c) : c);
                nextUpper = false;
            }
        }
        return result.toString();
    }
}
