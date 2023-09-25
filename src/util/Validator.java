package util;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Validator {
    // Define a regex pattern to match specific symbols
    private static final String SYMBOL_PATTERN = "[,./<>?;':\"~!@#$%^&*()_+\\-=\\{\\}\\[\\]]";

    public static boolean containsSymbol(String text) {
        Pattern pattern = Pattern.compile(SYMBOL_PATTERN);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    public static boolean isInputEmpty(String text) {
        return text.trim().isEmpty();
    }
    
    public static boolean containsNonNumeric(String input) {
    // Use a regex pattern to check if the input contains at least one letter or non-numeric character
    Pattern pattern = Pattern.compile("[a-zA-Z\\W]");
    Matcher matcher = pattern.matcher(input);
    return matcher.find();
}
}
