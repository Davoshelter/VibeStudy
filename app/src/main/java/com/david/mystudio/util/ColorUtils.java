package com.david.mystudio.util;

import android.graphics.Color;

/**
 * Utilitario para validación y conversión de cadenas de color hexadecimal.
 */
public final class ColorUtils {

    public static final String COLOR_POR_DEFECTO_HEX = "#1E88E5";
    private static final String PATRON_HEX = "^#[0-9A-Fa-f]{6}$";

    private ColorUtils() {
    }

    public static boolean esColorHexValido(String colorHex) {
        return colorHex != null && colorHex.matches(PATRON_HEX);
    }

    public static int parseColorConFallback(String colorHex, int fallbackColor) {
        if (esColorHexValido(colorHex)) {
            try {
                return Color.parseColor(colorHex);
            } catch (IllegalArgumentException e) {
                return fallbackColor;
            }
        }
        return fallbackColor;
    }
}
