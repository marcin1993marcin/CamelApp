package com.app.camel.util;

public class Precondition {

    public static boolean isInteger(String value) {
        return value.matches("^-?\\d+$");
    }

}
