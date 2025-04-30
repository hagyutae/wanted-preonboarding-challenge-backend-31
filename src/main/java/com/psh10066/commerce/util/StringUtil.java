package com.psh10066.commerce.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class StringUtil {

    public static String snakeToCamel(String snake) {
        String[] parts = snake.split("_");
        if (parts.length == 1) {
            return snake;
        }
        StringBuilder sb = new StringBuilder(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            String p = parts[i];
            if (p.length() > 0) {
                sb.append(Character.toUpperCase(p.charAt(0)))
                    .append(p.substring(1));
            }
        }
        return sb.toString();
    }

    public static String pascalToCamel(String pascal) {
        return pascal.substring(0, 1).toLowerCase() + pascal.substring(1);
    }
}
