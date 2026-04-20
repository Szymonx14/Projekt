package com.example.workouttracker.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.workouttracker.R;
import com.example.workouttracker.database.WorkoutDAO;
import com.example.workouttracker.models.Workout;

import java.util.Calendar;

public class AddWorkoutActivity extends AppCompatActivity {

    private EditText etWorkoutName, etDate, etDuration, etNotes;
    private Spinner spinnerType;
    private Button btnSaveWorkout;
    private WorkoutDAO workoutDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        workoutDAO = new WorkoutDAO(this);

        etWorkoutName = findViewById(R.id.etWorkoutName);
        etDate = findViewById(R.id.etDate);
        etDuration = findViewById(R.id.etDuration);
        etNotes = findViewById(R.id.etNotes);
        spinnerType = findViewById(R.id.spinnerType);
        btnSaveWorkout = findViewById(R.id.btnSaveWorkout);

        // Ustawienie Spinnera z typami treningu
        String[] types = {"Siłowy", "Cardio", "Rozciąganie", "Inny"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        // Kliknięcie w datę otwiera kalendarz
        etDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(this,
                    (view, y, m, d) -> {
                        String date = y + "-" + String.format("%02d", m + 1) + "-" + String.format("%02d", d);
                        etDate.setText(date);
                    }, year, month, day);
            datePicker.show();
        });


        btnSaveWorkout.setOnClickListener(v -> saveWorkout());
    }

    private void saveWorkout() {
        String name = etWorkoutName.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String durationStr = etDuration.getText().toString().trim();
        String notes = etNotes.getText().toString().trim();
        String type = spinnerType.getSelectedItem().toString();

        if (name.isEmpty()) {
            etWorkoutName.setError("Podaj nazwę treningu");
            return;
        }
        if (date.isEmpty()) {
            etDate.setError("Wybierz datę");
            return;
        }
        if (durationStr.isEmpty()) {
            etDuration.setError("Podaj czas trwania");
            return;
        }

        int duration = Integer.parseInt(durationStr);
        Workout workout = new Workout(name, type, date, duration, notes);

        workoutDAO.open();
        workoutDAO.addWorkout(workout);
        workoutDAO.close();

        Toast.makeText(this, "Trening zapisany!", Toast.LENGTH_SHORT).show();
        finish();
    }
}