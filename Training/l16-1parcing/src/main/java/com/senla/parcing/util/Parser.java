package com.senla.parcing.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public static String parse(String src, boolean spaceParse, boolean mobileNumberPurse,
                               boolean uppercaseParse, boolean tagParse, boolean uriParse) {
        if (spaceParse) {
            src = parseSpace(src);
        }
        if (mobileNumberPurse) {
            src = parseMobileNumber(src);
        }
        if (uppercaseParse) {
            src = parseToUpperCase(src);
        }
        if (tagParse) {
            src = parseTextInTags(src);
        }
        if (uriParse) {
            src = parseUri(src);
        }
        return src;
    }

    private static String parseSpace(String src) {
        return src.trim().replaceAll(" +", "-");
    }

    private static String parseMobileNumber(String src) {
        Matcher mt = Pattern.compile("\\b8 \\(0\\d{2}\\) \\d{3}\\-\\d{2}\\-\\d{2}\\b").matcher(src);
        StringBuilder sb = new StringBuilder();
        while (mt.find()) {
            sb.append(mt.group());
        }
        return sb.toString();
    }

    private static String parseToUpperCase(String src) {
        Matcher mt = Pattern.compile("\\b([А-яA-z]{4})\\b").matcher(src);
        StringBuffer sb = new StringBuffer();
        while (mt.find()) {
            mt.appendReplacement(sb, mt.group().toUpperCase());
        }
        return mt.appendTail(sb).toString();
    }

    private static String parseTextInTags(String src) {
        StringBuilder sb = new StringBuilder();
        Matcher mt = Pattern.compile("<one>([^<one>.+<\\/one]+)<\\/one>").matcher(src);
        while (mt.find()) {
            sb.append(mt.group(1))
                    .append("\n");
        }
        return sb.toString();
    }

    private static String parseUri(String src) {
        return src.replaceAll(" (www(\\.[a-z0-9\\-]+)+) ", " http://$1 ");

    }
}
