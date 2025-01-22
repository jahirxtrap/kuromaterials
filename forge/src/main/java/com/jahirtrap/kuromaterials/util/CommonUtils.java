package com.jahirtrap.kuromaterials.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class CommonUtils {
    public static Component coloredTextComponent(String string, ChatFormatting color) {
        var textComponent = Component.literal(string);
        textComponent.withStyle(color);
        return textComponent;
    }

    public static String snakeToTitleCase(String string) {
        var result = new StringBuilder();
        for (String word : string.split("_"))
            if (!word.isEmpty())
                result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1).toLowerCase()).append(" ");

        return result.toString().trim();
    }
}
