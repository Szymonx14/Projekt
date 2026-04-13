package com.example.workouttracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "workout_tracker.db";
    private static final int DATABASE_VERSION = 1;

    // Tabela treningów
    public static final String TABLE_WORKOUTS = "workouts";
    public static final String COLUMN_WORKOUT_ID = "id";
    public static final String COLUMN_WORKOUT_NAME = "name";
    public static final String COLUMN_WORKOUT_TYPE = "type";
    public static final String COLUMN_WORKOUT_DATE = "date";
    public static final String COLUMN_WORKOUT_DURATION = "duration";
    public static final String COLUMN_WORKOUT_NOTES = "notes";

    // Tabela ćwiczeń
    public static final String TABLE_EXERCISES = "exercises";
    public static final String COLUMN_EXERCISE_ID = "id";
    public static final String COLUMN_EXERCISE_WORKOUT_ID = "workout_id";
    public static final String COLUMN_EXERCISE_NAME = "name";
    public static final String COLUMN_EXERCISE_SETS = "sets";
    public static final String COLUMN_EXERCISE_REPS = "reps";
    public static final String COLUMN_EXERCISE_WEIGHT = "weight";

    private static final String CREATE_TABLE_WORKOUTS =
            "CREATE TABLE " + TABLE_WORKOUTS + " (" +
                    COLUMN_WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_WORKOUT_NAME + " TEXT, " +
                    COLUMN_WORKOUT_TYPE + " TEXT, " +
                    COLUMN_WORKOUT_DATE + " TEXT, " +
                    COLUMN_WORKOUT_DURATION + " INTEGER, " +
                    COLUMN_WORKOUT_NOTES + " TEXT)";

    private static final String CREATE_TABLE_EXERCISES =
            "CREATE TABLE " + TABLE_EXERCISES + " (" +
                    COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_EXERCISE_WORKOUT_ID + " INTEGER, " +
                    COLUMN_EXERCISE_NAME + " TEXT, " +
                    COLUMN_EXERCISE_SETS + " INTEGER, " +
                    COLUMN_EXERCISE_REPS + " INTEGER, " +
                    COLUMN_EXERCISE_WEIGHT + " REAL, " +
                    "FOREIGN KEY(" + COLUMN_EXERCISE_WORKOUT_ID + ") REFERENCES " +
                    TABLE_WORKOUTS + "(" + COLUMN_WORKOUT_ID + "))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_WORKOUTS);
        db.execSQL(CREATE_TABLE_EXERCISES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTS);
        onCreate(db);
    }
}
