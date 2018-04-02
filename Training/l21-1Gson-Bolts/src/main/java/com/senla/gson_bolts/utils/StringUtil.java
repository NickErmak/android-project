package com.senla.gson_bolts.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {

    public static final String ERROR = "error:";
    public static final String EMAIL_REGEX = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";

    public static String getOutputError(String message) {
        return ERROR + "\t" + message;
    }

    public static String getBirthDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        return sdf.format(date);
    }

    public static boolean checkEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }
}
