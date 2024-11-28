package com.curconv.currency.converter.security;

public class AppRegex {
    //Match to country code
    public static final String THREE_DIGITS_COUNTRY_CODE = "^[A-Z]{3}$";
    public static final String TWO_DIGITS_COUNTRY_CODE = "^[A-Z]{2}$";
    public static final String COUNTRY_NAME_CASE_INSENSITIVE = "(?i)^[A-Z]*$";
}
