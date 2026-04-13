package com.example.workouttracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.workouttracker.models.Exercise;
import com.example.workouttracker.models.Workout;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDAO {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public WorkoutDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // ========== TRENINGI ==========

    public long addWorkout(Workout workout) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_WORKOUT_NAME, workout.getName());
        values.put(DatabaseHelper.COLUMN_WORKOUT_TYPE, workout.getType());
        values.put(DatabaseHelper.COLUMN_WORKOUT_DATE, workout.getDate());
        values.put(DatabaseHelper.COLUMN_WORKOUT_DURATION, workout.getDuration());
        values.put(DatabaseHelper.COLUMN_WORKOUT_NOTES, workout.getNotes());
        return database.insert(DatabaseHelper.TABLE_WORKOUTS, null, values);
    }

    public List<Workout> getAllWorkouts() {
        List<Workout> workouts = new ArrayList<>();
        Cursor cursor = database.query(
                DatabaseHelper.TABLE_WORKOUTS,
                null, null, null, null, null,
                DatabaseHelper.COLUMN_WORKOUT_DATE + " DESC"
        );
        if (cursor.moveToFirst()) {
            do {
                Workout workout = new Workout();
                workout.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_WORKOUT_ID)));
                workout.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_WORKOUT_NAME)));
                workout.setType(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_WORKOUT_TYPE)));
                workout.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_WORKOUT_DATE)));
                workout.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_WORKOUT_DURATION)));
                workout.setNotes(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_WORKOUT_NOTES)));
                workouts.add(workout);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return workouts;
    }

    public void deleteWorkout(int workoutId) {
        database.delete(DatabaseHelper.TABLE_EXERCISES,
                DatabaseHelper.COLUMN_EXERCISE_WORKOUT_ID + "=?",
                new String[]{String.valueOf(workoutId)});
        database.delete(DatabaseHelper.TABLE_WORKOUTS,
                DatabaseHelper.COLUMN_WORKOUT_ID + "=?",
                new String[]{String.valueOf(workoutId)});
    }

    // ========== ĆWICZENIA ==========

    public long addExercise(Exercise exercise) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EXERCISE_WORKOUT_ID, exercise.getWorkoutId());
        values.put(DatabaseHelper.COLUMN_EXERCISE_NAME, exercise.getName());
        values.put(DatabaseHelper.COLUMN_EXERCISE_SETS, exercise.getSets());
        values.put(DatabaseHelper.COLUMN_EXERCISE_REPS, exercise.getReps());
        values.put(DatabaseHelper.COLUMN_EXERCISE_WEIGHT, exercise.getWeight());
        return database.insert(DatabaseHelper.TABLE_EXERCISES, null, values);
    }

    public List<Exercise> getExercisesForWorkout(int workoutId) {
        List<Exercise> exercises = new ArrayList<>();
        Cursor cursor = database.query(
                DatabaseHelper.TABLE_EXERCISES,
                null,
                DatabaseHelper.COLUMN_EXERCISE_WORKOUT_ID + "=?",
                new String[]{String.valueOf(workoutId)},
                null, null, null
        );
        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = new Exercise();
                exercise.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXERCISE_ID)));
                exercise.setWorkoutId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXERCISE_WORKOUT_ID)));
                exercise.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXERCISE_NAME)));
                exercise.setSets(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXERCISE_SETS)));
                exercise.setReps(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXERCISE_REPS)));
                exercise.setWeight(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EXERCISE_WEIGHT)));
                exercises.add(exercise);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return exercises;
    }

    public void deleteExercise(int exerciseId) {
        database.delete(DatabaseHelper.TABLE_EXERCISES,
                DatabaseHelper.COLUMN_EXERCISE_ID + "=?",
                new String[]{String.valueOf(exerciseId)});
    }

    // ========== STATYSTYKI ==========

    public int getTotalWorkouts() {
        Cursor cursor = database.rawQuery(
                "SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_WORKOUTS, null);
        int count = 0;
        if (cursor.moveToFirst()) count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public int getTotalDuration() {
        Cursor cursor = database.rawQuery(
                "SELECT SUM(duration) FROM " + DatabaseHelper.TABLE_WORKOUTS, null);
        int total = 0;
        if (cursor.moveToFirst()) total = cursor.getInt(0);
        cursor.close();
        return total;
    }

    public int getWorkoutsThisWeek() {
        Cursor cursor = database.rawQuery(
                "SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_WORKOUTS +
                        " WHERE date >= date('now', '-7 days')", null);
        int count = 0;
        if (cursor.moveToFirst()) count = cursor.getInt(0);
        cursor.close();
        return count;
    }
}
