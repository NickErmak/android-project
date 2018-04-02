package com.senla.sqlite;


import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.senla.sqlite.helpers.DbOpenHelper;

public class App extends Application {
    private static App instance;
    private SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        db = new DbOpenHelper(this).getWritableDatabase();
    }

    public static App getInstance() {
        return App.instance;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
