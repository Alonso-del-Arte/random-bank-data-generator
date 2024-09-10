package textops;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TextCalculator {

    private static final Random RANDOM = new Random();

    /**
     * Gives a {@code String} of ASCII characters of specified length.
     * @param len How long the result should be. For example, 25. May be 0, but
     *            preferably greater.
     * @return A {@code String} with as many characters as specified by {@code
     * len}. For example, "K$k1A6LQuRa@RDTkimg#$_w'1".
     * @throws NegativeArraySizeException If {@code len} is negative.
     */
    public static String randomASCIIChars(int len) {
        char[] characters = new char[len];
        for (int i = 0; i < len; i++) {
            char ch = (char) (RANDOM.nextInt(95) + 32);
            characters[i] = ch;
        }
        return new String(characters);
    }

    // TODO: Write tests for this
    public static String randomUnicodeChars(int len) {
        if (len < 0) {
            String excMsg = "Length " + len + " is not valid";
            throw new NegativeArraySizeException(excMsg);
        }
        return "SORRY, NOT IMPLEMENTED YET";
    }

    // TODO: Write tests for this
    public static String randomUnicodeChars(int len, Character.UnicodeBlock block) {
        char[] characters = new char[len];
        Arrays.fill(characters, 'X');
        return new String(characters);
    }

    /**
     * Pads a {@code String} with a specified character.
     * @param s The {@code String} to pad. For example, "Hello, world!".
     * @param len The length the padded {@code String} should be. For example,
     *            16.
     * @param pad The character to pad with. For example, '_' (underscore).
     * @return The padded {@code String}. For example, "___Hello, world!".
     */
    public static String leftPad(String s, int len, char pad) {
        int deficit = len - s.length();
        char[] padding = new char[deficit];
        Arrays.fill(padding, pad);
        String prefix = new String(padding);
        return "SORRY, NOT IMPLEMENTED YET";// prefix + s;
    }

    // TODO: Write tests for this
    public static String rightPad(String s, int len, char pad) {
        return "SORRY, NOT IMPLEMENTED YET";
    }

    private static class BMPCharPair {

        final char beginChar, endChar;

        BMPCharPair(char from, char to) {
            this.beginChar = from;
            this.endChar = to;
        }

    }

}
