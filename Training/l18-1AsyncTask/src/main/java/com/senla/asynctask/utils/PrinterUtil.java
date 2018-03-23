package com.senla.asynctask.utils;

import android.widget.TextView;

import java.util.Iterator;
import java.util.List;

public class PrinterUtil {

    private static final String NEW_LINE = "\n";

    public static void append(TextView tv, String msg) {
        tv.append(msg + NEW_LINE);
    }

    public static void append(TextView tv, List<String> msgs) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = msgs.iterator();
        while (iterator.hasNext()) {
            String msg = iterator.next();
            sb.append(msg)
                    .append(NEW_LINE);
        }
        tv.append(sb.toString());
    }
}
