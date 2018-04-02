package com.senla.sqlite.helpers;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.senla.sqlite.App;
import com.senla.sqlite.R;


public class DbOpenHelper extends SQLiteOpenHelper {

    private static final Resources resources = App.getInstance().getResources();
    public static final String DB_NAME = resources.getString(R.string.db_name);
    public static final int DB_VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
        fillTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void createTables(SQLiteDatabase db) {
        String[] tables = resources.getStringArray(R.array.tables);
        for (String sqlCreate : tables) {
            DbCrudHelper.exec(db, sqlCreate);
        }
    }

    private void fillTables(SQLiteDatabase db) {
        fillTable(db, App.getInstance().getString(R.string.sql_insert_user), R.array.users);
        fillTable(db, App.getInstance().getString(R.string.sql_insert_post), R.array.posts);
        fillTable(db, App.getInstance().getString(R.string.sql_insert_comment), R.array.comments);
    }

    private void fillTable(SQLiteDatabase db, String sqlInsert, int entitiesArrayId) {
        TypedArray entitiesArray = resources.obtainTypedArray(entitiesArrayId);
        for (int i = 0; i < entitiesArray.length(); i++) {
            DbCrudHelper.insert(
                    db,
                    sqlInsert,
                    resources.getStringArray(entitiesArray.getResourceId(i, 0)));
        }
        entitiesArray.recycle();
    }
}
