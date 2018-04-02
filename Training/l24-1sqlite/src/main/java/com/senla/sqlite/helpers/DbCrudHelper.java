package com.senla.sqlite.helpers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.senla.sqlite.App;
import com.senla.sqlite.R;

public class DbCrudHelper {

    public static void exec(SQLiteDatabase db, String sql) {
        db.execSQL(sql);
    }

    public static long insert(SQLiteDatabase db, String sql, String[] columns) {
        long idInsert;
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindAllArgsAsStrings(columns);
        try {
            idInsert = statement.executeInsert();
        } finally {
            statement.close();
        }
        return idInsert;
    }

    public static Cursor getPosts() {
        return App.getInstance().getDb().rawQuery(
                App.getInstance().getString(R.string.sql_select_posts),
                null
        );
    }

    public static Cursor getComments(long id) {
        return App.getInstance().getDb().rawQuery(
                App.getInstance().getString(R.string.sql_select_comments),
                new String[]{String.valueOf(id)}
        );
    }
}
