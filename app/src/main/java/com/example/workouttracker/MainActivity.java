package com.example.workouttracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.workouttracker.activities.AddWorkoutActivity;
import com.example.workouttracker.activities.WorkoutListActivity;
import com.example.workouttracker.activities.StatsActivity;
import com.example.workouttracker.database.WorkoutDAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private WorkoutDAO workoutDAO;
    private TextView tvDate, tvTotalWorkouts, tvWeekWorkouts, tvTotalTime;
    private Button btnAddWorkout, btnViewWorkouts, btnStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        workoutDAO = new WorkoutDAO(this);


        tvDate = findViewById(R.id.tvDate);
        tvTotalWorkouts = findViewById(R.id.tvTotalWorkouts);
        tvWeekWorkouts = findViewById(R.id.tvWeekWorkouts);
        tvTotalTime = findViewById(R.id.tvTotalTime);
        btnAddWorkout = findViewById(R.id.btnAddWorkout);
        btnViewWorkouts = findViewById(R.id.btnViewWorkouts);
        btnStats = findViewById(R.id.btnStats);

        // Ustawienie dzisiejszej daty
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMMM", new Locale("pl", "PL"));
        tvDate.setText(sdf.format(new Date()));


        btnAddWorkout.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddWorkoutActivity.class);
            startActivity(intent);
        });

        btnViewWorkouts.setOnClickListener(v -> {
            Intent intent = new Intent(this, WorkoutListActivity.class);
            startActivity(intent);
        });

        btnStats.setOnClickListener(v -> {
            Intent intent = new Intent(this, StatsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Odświeżenie statystyk przy powrocie na ekran
        workoutDAO.open();
        tvTotalWorkouts.setText(String.valueOf(workoutDAO.getTotalWorkouts()));
        tvWeekWorkouts.setText(String.valueOf(workoutDAO.getWorkoutsThisWeek()));
        tvTotalTime.setText(String.valueOf(workoutDAO.getTotalDuration()));
        workoutDAO.close();
    }
}