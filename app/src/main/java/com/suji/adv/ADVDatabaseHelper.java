package com.suji.adv;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ADVDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "advDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public ADVDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ADV_TABLE = "CREATE TABLE adv_info (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "platform TEXT, " +
                "time TEXT, " +
                "cpm REAL)";
        db.execSQL(CREATE_ADV_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS adv_info");
        onCreate(db);
    }
}
