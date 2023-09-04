package util;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SymbolValidator {
    // Define a regex pattern to match the specific symbol
    private static final String SYMBOL_PATTERN = "[,./<>?;':\"~!@#$%^&*()_+\\-=\\{\\}\\[\\]]";

    public static boolean containsSymbol(String text) {
        Pattern pattern = Pattern.compile(SYMBOL_PATTERN);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }
}