package com.wen.flow.enums;

public enum  RegexPatternEnum {
    EMAIL("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$",0),
    PASS_WORD("^[a-zA-Z0-9]{8,16}$",1),
    EMAIL_CODE("\\d{6,}",2);


    RegexPatternEnum(String regex, int type) {
        this.regex = regex;
        this.type = type;

    }

    String regex;
    int type;

    public String getRegex() {
        return regex;
    }

    public int getCode() {
        return type;
    }
}
