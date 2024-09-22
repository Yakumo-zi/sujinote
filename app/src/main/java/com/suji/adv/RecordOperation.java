package com.suji.adv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class RecordOperation {
    SQLiteOpenHelper dbHandler;
    SQLiteDatabase db;

    public RecordOperation(Context context) {
        dbHandler = new ADVDatabaseHelper(context);
        open();
    }

    public void open() throws SQLException {
        db = dbHandler.getWritableDatabase();
    }

    public void close() {
        dbHandler.close();
    }

    public ADVEntity create(ADVEntity entity) {
        ContentValues values = new ContentValues();
        values.put("platform", entity.getPlatform());
        values.put("time", entity.getTime());
        values.put("cpm", entity.getCpm());
        long id = db.insert("adv_info", null, values);
        entity.setID(id);
        return entity;
    }

    public List<ADVEntity> query() {
        List<ADVEntity> entityList = new ArrayList<>();
        Cursor cursor = db.query("adv_info",
                new String[]{"id", "platform", "time", "cpm"},
                null, null, null, null, "id DESC limit 10");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                ADVEntity entity = new ADVEntity();
                entity.setID(cursor.getLong(cursor.getColumnIndexOrThrow("id")));
                entity.setPlatform(cursor.getString(cursor.getColumnIndexOrThrow("platform")));
                entity.setTime(cursor.getString(cursor.getColumnIndexOrThrow("time")));
                entity.setCpm(cursor.getFloat(cursor.getColumnIndexOrThrow("cpm")));
                entityList.add(entity);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return entityList;
    }

    public int update(ADVEntity entity) {
        ContentValues values = new ContentValues();
        values.put("platform", entity.getPlatform());
        values.put("time", entity.getTime());
        values.put("cpm", entity.getCpm());

        return db.update("adv_info", values, "id = ?",
                new String[]{String.valueOf(entity.getID())});
    }

    public void delete(long id) {
        db.delete("adv_info", "id = ?", new String[]{String.valueOf(id)});
    }
}
