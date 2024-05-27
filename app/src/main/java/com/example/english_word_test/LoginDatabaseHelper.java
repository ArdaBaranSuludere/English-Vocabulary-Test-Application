package com.example.english_word_test;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LoginDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME = "UserScore.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_SCORE = "score";

    LoginDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_SCORE + " INTEGER DEFAULT 0);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    boolean checkUser(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        return cursor.getCount() > 0;
    }

    long addUser(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed to register", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    @SuppressLint("Range")
    int getScore(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_SCORE + " FROM " + TABLE_NAME + " WHERE " +
                COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        int score = 0;
        if (cursor.moveToFirst()) {
            score = cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE));
        } else {
            Log.w("GetScoreWarning", "No score found for username: " + username);
        }
        cursor.close();
        return score;
    }

    void updateScore(String username, int newScore) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            int currentScore = getScore(username);
            int updatedScore = currentScore + newScore;

            ContentValues cv = new ContentValues();
            cv.put(COLUMN_SCORE, updatedScore);
            db.update(TABLE_NAME, cv, COLUMN_USERNAME + " = ?", new String[]{username});
        } catch (Exception e) {
            Log.e("UpdateScoreError", "Error updating score for username: " + username, e);
        }
    }

    // Liderlik tablosunu getiren fonksiyon
    public List<User> getLeaderboard() {
        List<User> leaderboard = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_SCORE + " DESC";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
                @SuppressLint("Range") int score = cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE));
                leaderboard.add(new User(username, score));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return leaderboard;
    }
}