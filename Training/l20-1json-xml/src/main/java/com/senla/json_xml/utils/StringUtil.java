package com.senla.json_xml.utils;

import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {

    public static final String NEW_LINE = "\n";
    public static final String EMAIL_REGEX = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";

    public static String getError(JsonParserUtil.Status status, String message) {
        return status.name() + NEW_LINE + message;
    }

    public static String getBirthDate (String unix) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(Integer.valueOf(unix)*1000L);
        return sdf.format(date);
    }

    public static boolean checkEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }
}
