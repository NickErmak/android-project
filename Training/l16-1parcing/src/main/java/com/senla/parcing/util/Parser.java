package com.senla.parcing.util;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public static String parseSpace(String src) {
        return src.trim().replaceAll(" +", "-");
    }

    public static List<String> parseMobileNumber(String src) {
        Matcher mt = Pattern.compile("\\b8 \\(0\\d{2}\\) \\d{3}\\-\\d{2}\\-\\d{2}\\b").matcher(src);
        List<String> numbers = new ArrayList<>();
        while (mt.find()) {
            numbers.add(mt.group());
        }
        return numbers;
    }

    public static String parseToUpperCase(String src) {
        Matcher mt = Pattern.compile("\\b([А-яA-z]{4})\\b").matcher(src);
        StringBuffer sb = new StringBuffer();
        while (mt.find()) {
            mt.appendReplacement(sb, mt.group().toUpperCase());
        }
        return mt.appendTail(sb).toString();
    }

    public static List<String> parseTextInTags(String src) {
        List<String> values = new ArrayList<>();
        Matcher mt = Pattern.compile("<one>([^<one>.+<\\/one]+)<\\/one>").matcher(src);
        while (mt.find()) {
            values.add(mt.group(1));
        }
        return values;
    }

    public static String parseUri(String src) {
        return src.replaceAll(" (www(\\.[a-z0-9\\-]+)+) ", " http://$1 ");

    }
}
