package com.ysc.graderank.util;

public class ComClass {

    public static final String NULL_SCORE = "未评定";

    public static boolean isEmptyStr(String string) {
        if (string == null || string.length() == 0) {
            return true;
        }
        for (int i = 0; i < string.length(); ++i) {
            if (string.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }
}
