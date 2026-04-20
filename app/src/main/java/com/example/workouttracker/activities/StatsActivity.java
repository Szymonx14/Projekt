package com.example.workouttracker.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.workouttracker.R;
import com.example.workouttracker.database.WorkoutDAO;
import com.example.workouttracker.models.Workout;

import java.util.List;

public class StatsActivity extends AppCompatActivity {

    private TextView tvStatTotalWorkouts, tvStatWeekWorkouts, tvStatTotalTime,
            tvStatAvgTime, tvStatStrength, tvStatCardio, tvStatStretching, tvStatOther;
    private WorkoutDAO workoutDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        workoutDAO = new WorkoutDAO(this);

        // Podpięcie widoków
        tvStatTotalWorkouts = findViewById(R.id.tvStatTotalWorkouts);
        tvStatWeekWorkouts = findViewById(R.id.tvStatWeekWorkouts);
        tvStatTotalTime = findViewById(R.id.tvStatTotalTime);
        tvStatAvgTime = findViewById(R.id.tvStatAvgTime);
        tvStatStrength = findViewById(R.id.tvStatStrength);
        tvStatCardio = findViewById(R.id.tvStatCardio);
        tvStatStretching = findViewById(R.id.tvStatStretching);
        tvStatOther = findViewById(R.id.tvStatOther);

        loadStats();
    }

    private void loadStats() {
        workoutDAO.open();

        int totalWorkouts = workoutDAO.getTotalWorkouts();
        int weekWorkouts = workoutDAO.getWorkoutsThisWeek();
        int totalTime = workoutDAO.getTotalDuration();
        int avgTime = totalWorkouts > 0 ? totalTime / totalWorkouts : 0;

        // Zliczanie typów treningów
        List<Workout> allWorkouts = workoutDAO.getAllWorkouts();
        int strength = 0, cardio = 0, stretching = 0, other = 0;
        for (Workout w : allWorkouts) {
            switch (w.getType()) {
                case "Siłowy": strength++; break;
                case "Cardio": cardio++; break;
                case "Rozciąganie": stretching++; break;
                default: other++; break;
            }
        }

        workoutDAO.close();

        // Ustawienie wartości
        tvStatTotalWorkouts.setText(String.valueOf(totalWorkouts));
        tvStatWeekWorkouts.setText(String.valueOf(weekWorkouts));
        tvStatTotalTime.setText(totalTime + " min");
        tvStatAvgTime.setText(avgTime + " min");
        tvStatStrength.setText(String.valueOf(strength));
        tvStatCardio.setText(String.valueOf(cardio));
        tvStatStretching.setText(String.valueOf(stretching));
        tvStatOther.setText(String.valueOf(other));
    }
}
