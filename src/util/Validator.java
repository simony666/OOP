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
    
    public static boolean containsOnlyAlphabetic(String input) {
        // Use a regex pattern to check if the input consists entirely of letters (alphabetic characters)
        Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}