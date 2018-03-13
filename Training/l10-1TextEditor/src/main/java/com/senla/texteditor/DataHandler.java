package com.senla.texteditor;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;



public class DataHandler {

    private static SharedPreferences sPref;
    private static SharedPreferences.Editor editor;
    private static final String FILE_NAME = "config";
    private static final String KEY_COLOR = "keyColor";
    private static final String KEY_SIZE = "keySize";

    public static enum Size {
        SIZE_12, SIZE_20
    }

    public static enum Color {
        BLACK, BLUE
    }

    static {
        sPref = App.self.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = sPref.edit();
    }

    public static Color loadColor() {
        String color = sPref.getString(KEY_COLOR, "0");
        return Color.valueOf(color);
    }

    public static Size loadSize() {
        String size = sPref.getString(KEY_SIZE, "0");
        return Size.valueOf(size);
    }

    public static void putColor(Color color) {
        editor.putString(KEY_COLOR, color.name())
                .commit();
    }

    public static void putSize(Size size) {
        editor.putString(KEY_SIZE, size.name())
                .commit();
    }
}
