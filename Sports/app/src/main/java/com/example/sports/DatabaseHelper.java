package com.example.sports;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Sports.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PLAYERS = "players";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_PLAYERS + " (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "category TEXT," +
                "matches_played INTEGER," +
                "scores INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        onCreate(db);
    }

    public boolean insertPlayer(int id, String name, String category, int matchesPlayed, int scores) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("category", category);
        contentValues.put("matches_played", matchesPlayed);
        contentValues.put("scores", scores);

        long result = db.insert(TABLE_PLAYERS, null, contentValues);
        return result != -1;
    }

    public Cursor getPlayersByCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PLAYERS + " WHERE category=? ORDER BY scores DESC", new String[]{category});
    }

    public boolean updatePlayerScores(int id, int scores) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("scores", scores);

        int result = db.update(TABLE_PLAYERS, contentValues, "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public boolean deletePlayer(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_PLAYERS, "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }
}
