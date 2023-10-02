package com.wen.flow.support.util;

import com.wen.flow.enums.RegexPatternEnum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    public static boolean censorText(String text, RegexPatternEnum regexPatternEnum) {
        String[] regex = {RegexPatternEnum.EMAIL.getRegex(),
                RegexPatternEnum.PASS_WORD.getRegex(),
                RegexPatternEnum.EMAIL_CODE.getRegex()};
        Pattern pattern = Pattern.compile(regex[regexPatternEnum.getCode()], Pattern.DOTALL | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

}
