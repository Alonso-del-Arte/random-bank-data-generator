package textops;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TextCalculator {

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

    // TODO: Write tests for this
    public static String leftPad(String s) {
        return "SORRY, NOT IMPLEMENTED YET";
    }

    // TODO: Write tests for this
    public static String rightPad(String s) {
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
