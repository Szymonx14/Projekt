package com.example.workouttracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttracker.R;
import com.example.workouttracker.adapters.ExerciseAdapter;
import com.example.workouttracker.database.WorkoutDAO;
import com.example.workouttracker.models.Exercise;

import java.util.List;

public class WorkoutDetailActivity extends AppCompatActivity {

    private TextView tvWorkoutTitle, tvDetailType, tvDetailDate, tvDetailDuration, tvDetailNotes, tvEmptyExercises;
    private RecyclerView recyclerExercises;
    private Button btnAddExercise;
    private WorkoutDAO workoutDAO;
    private ExerciseAdapter adapter;
    private List<Exercise> exerciseList;
    private int workoutId;
    private String workoutName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);

        workoutDAO = new WorkoutDAO(this);

        // Pobranie danych z poprzedniego ekranu
        workoutId = getIntent().getIntExtra("workout_id", -1);
        workoutName = getIntent().getStringExtra("workout_name");

        // Podpięcie widoków
        tvWorkoutTitle = findViewById(R.id.tvWorkoutTitle);
        tvDetailType = findViewById(R.id.tvDetailType);
        tvDetailDate = findViewById(R.id.tvDetailDate);
        tvDetailDuration = findViewById(R.id.tvDetailDuration);
        tvDetailNotes = findViewById(R.id.tvDetailNotes);
        tvEmptyExercises = findViewById(R.id.tvEmptyExercises);
        recyclerExercises = findViewById(R.id.recyclerExercises);
        btnAddExercise = findViewById(R.id.btnAddExercise);

        recyclerExercises.setLayoutManager(new LinearLayoutManager(this));

        tvWorkoutTitle.setText(workoutName);

        // Wczytanie szczegółów treningu
        workoutDAO.open();
        loadWorkoutDetails();
        workoutDAO.close();

        btnAddExercise.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddExerciseActivity.class);
            intent.putExtra("workout_id", workoutId);
            startActivity(intent);
        });
    }

    private void loadWorkoutDetails() {
        // Pobranie szczegółów treningu
        workoutDAO.open();
        List<com.example.workouttracker.models.Workout> workouts = workoutDAO.getAllWorkouts();
        for (com.example.workouttracker.models.Workout w : workouts) {
            if (w.getId() == workoutId) {
                tvDetailType.setText(w.getType());
                tvDetailDate.setText(w.getDate());
                tvDetailDuration.setText(w.getDuration() + " min");
                tvDetailNotes.setText(w.getNotes().isEmpty() ? "-" : w.getNotes());
                break;
            }
        }

        // Pobranie ćwiczeń
        exerciseList = workoutDAO.getExercisesForWorkout(workoutId);
        workoutDAO.close();

        if (exerciseList.isEmpty()) {
            tvEmptyExercises.setVisibility(View.VISIBLE);
            recyclerExercises.setVisibility(View.GONE);
        } else {
            tvEmptyExercises.setVisibility(View.GONE);
            recyclerExercises.setVisibility(View.VISIBLE);
        }

        adapter = new ExerciseAdapter(this, exerciseList, exercise -> {
            new AlertDialog.Builder(this)
                    .setTitle("Usuń ćwiczenie")
                    .setMessage("Czy na pewno chcesz usunąć \"" + exercise.getName() + "\"?")
                    .setPositiveButton("Usuń", (dialog, which) -> {
                        workoutDAO.open();
                        workoutDAO.deleteExercise(exercise.getId());
                        workoutDAO.close();
                        Toast.makeText(this, "Ćwiczenie usunięte", Toast.LENGTH_SHORT).show();
                        loadWorkoutDetails();
                    })
                    .setNegativeButton("Anuluj", null)
                    .show();
        });

        recyclerExercises.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWorkoutDetails();
    }
}