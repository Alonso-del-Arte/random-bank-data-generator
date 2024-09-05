package textops;

import static currency.CurrencyAmountTest.RANDOM;

import static java.lang.Character.UnicodeBlock.*;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextCalculatorTest {

    /**
     * A somewhat arbitrary selection of Unicode blocks from the Basic
     * Multilingual Plane (BMP) to be used in testing.
     */
    private static final Character.UnicodeBlock[] UNICODE_BLOCKS
            = {BASIC_LATIN, LATIN_1_SUPPLEMENT, LATIN_EXTENDED_A,
            LATIN_EXTENDED_B, IPA_EXTENSIONS, GREEK, CYRILLIC,
            CYRILLIC_SUPPLEMENTARY, ARMENIAN, HEBREW, ARABIC, SYRIAC,
            ARABIC_SUPPLEMENT, THAANA, NKO, SAMARITAN, MANDAIC,
            SYRIAC_SUPPLEMENT, ARABIC_EXTENDED_B, ARABIC_EXTENDED_A, DEVANAGARI,
            BENGALI, GURMUKHI, GUJARATI, ORIYA, TAMIL, TELUGU, KANNADA,
            MALAYALAM, SINHALA, THAI, LAO, TIBETAN, MYANMAR, GEORGIAN,
            HANGUL_JAMO, ETHIOPIC, ETHIOPIC_SUPPLEMENT, CHEROKEE,
            UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS, OGHAM, RUNIC, TAGALOG,
            HANUNOO, BUHID, TAGBANWA, KHMER, MONGOLIAN,
            UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS_EXTENDED, LIMBU, TAI_LE,
            NEW_TAI_LUE, BUGINESE, TAI_THAM, BALINESE, SUNDANESE, BATAK,
            LEPCHA, OL_CHIKI, CYRILLIC_EXTENDED_C, GEORGIAN_EXTENDED,
            SUNDANESE_SUPPLEMENT, VEDIC_EXTENSIONS, LATIN_EXTENDED_ADDITIONAL,
            GREEK_EXTENDED, GLAGOLITIC, LATIN_EXTENDED_C, COPTIC,
            GEORGIAN_SUPPLEMENT, TIFINAGH, ETHIOPIC_EXTENDED,
            CYRILLIC_EXTENDED_A, HIRAGANA, KATAKANA, BOPOMOFO, KANBUN,
            BOPOMOFO_EXTENDED, CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A,
            CJK_UNIFIED_IDEOGRAPHS, YI_SYLLABLES, LISU, VAI,
            CYRILLIC_EXTENDED_B, BAMUM, LATIN_EXTENDED_D, SYLOTI_NAGRI,
            PHAGS_PA, SAURASHTRA, DEVANAGARI_EXTENDED, KAYAH_LI, REJANG,
            HANGUL_JAMO_EXTENDED_A, JAVANESE, MYANMAR_EXTENDED_B, CHAM,
            MYANMAR_EXTENDED_A, TAI_VIET, MEETEI_MAYEK_EXTENSIONS,
            ETHIOPIC_EXTENDED_A, LATIN_EXTENDED_E, CHEROKEE_SUPPLEMENT,
            MEETEI_MAYEK, HANGUL_SYLLABLES, HANGUL_JAMO_EXTENDED_B};

    private static final int NUMBER_OF_BLOCKS_TO_CHOOSE_FROM
            = UNICODE_BLOCKS.length;

    private Character.UnicodeBlock chooseBlock() {
        return UNICODE_BLOCKS[RANDOM.nextInt(NUMBER_OF_BLOCKS_TO_CHOOSE_FROM)];
    }

    @Test
    void testRandomUnicodeCharsRejectsNegativeLength() {
        Character.UnicodeBlock block = chooseBlock();
        int badLen = -RANDOM.nextInt(128) - 1;
        String msg = "Bad length " + badLen + " should cause exception";
        Throwable t = assertThrows(NegativeArraySizeException.class, () -> {
            String badResult = TextCalculator.randomUnicodeChars(badLen, block);
            System.out.println(msg + " not given result \"" + badResult + "\"");
        }, msg);
        String excMsg = t.getMessage();
        assert excMsg != null : "Exception message should not be null";
        assert !excMsg.isBlank() : "Exception message should not be blank";
        String containsMsg = "Exception message should contain bad length " + badLen;
        assert excMsg.contains(Integer.toString(badLen)) : containsMsg;
        System.out.println("\"" + excMsg + "\"");
    }

    private static void assertCharIsFromBlock(char ch, Character.UnicodeBlock expected) {
        Character.UnicodeBlock actual = Character.UnicodeBlock.of(ch);
        String msg = "Character '" + ch + "' (" + Character.getName(ch)
                + ") should be of block " + expected.toString();
        assertEquals(expected, actual, msg);
    }

    @Test
    void testRandomUnicodeChars() {
        System.out.println("randomUnicodeChars");
        Character.UnicodeBlock block = chooseBlock();
        int expLen = RANDOM.nextInt(32) + 8;
        String s = TextCalculator.randomUnicodeChars(expLen, block);
        int actLen = s.length();
        String msg = "String \"" + s + "\" should be of length " + expLen;
        assertEquals(expLen, actLen, msg);
        char[] compChars = s.toCharArray();
        for (char ch : compChars) {
            assertCharIsFromBlock(ch, block);
        }
    }

}
