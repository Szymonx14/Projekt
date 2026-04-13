package com.example.workouttracker.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.workouttracker.R;
import com.example.workouttracker.database.WorkoutDAO;
import com.example.workouttracker.models.Exercise;

public class AddExerciseActivity extends AppCompatActivity {

    private EditText etExerciseName, etSets, etReps, etWeight;
    private Button btnSaveExercise;
    private WorkoutDAO workoutDAO;
    private int workoutId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        workoutDAO = new WorkoutDAO(this);

        // Pobranie ID treningu z poprzedniego ekranu
        workoutId = getIntent().getIntExtra("workout_id", -1);

        // Podpięcie widoków
        etExerciseName = findViewById(R.id.etExerciseName);
        etSets = findViewById(R.id.etSets);
        etReps = findViewById(R.id.etReps);
        etWeight = findViewById(R.id.etWeight);
        btnSaveExercise = findViewById(R.id.btnSaveExercise);

        btnSaveExercise.setOnClickListener(v -> saveExercise());
    }

    private void saveExercise() {
        String name = etExerciseName.getText().toString().trim();
        String setsStr = etSets.getText().toString().trim();
        String repsStr = etReps.getText().toString().trim();
        String weightStr = etWeight.getText().toString().trim();

        // Walidacja
        if (name.isEmpty()) {
            etExerciseName.setError("Podaj nazwę ćwiczenia");
            return;
        }
        if (setsStr.isEmpty()) {
            etSets.setError("Podaj liczbę serii");
            return;
        }
        if (repsStr.isEmpty()) {
            etReps.setError("Podaj liczbę powtórzeń");
            return;
        }
        if (weightStr.isEmpty()) {
            etWeight.setError("Podaj ciężar");
            return;
        }

        int sets = Integer.parseInt(setsStr);
        int reps = Integer.parseInt(repsStr);
        double weight = Double.parseDouble(weightStr);

        Exercise exercise = new Exercise(workoutId, name, sets, reps, weight);

        workoutDAO.open();
        workoutDAO.addExercise(exercise);
        workoutDAO.close();

        Toast.makeText(this, "Ćwiczenie zapisane!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
