package com.example.workouttracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttracker.R;
import com.example.workouttracker.adapters.WorkoutAdapter;
import com.example.workouttracker.database.WorkoutDAO;
import com.example.workouttracker.models.Workout;

import java.util.List;

public class WorkoutListActivity extends AppCompatActivity {

    private RecyclerView recyclerWorkouts;
    private TextView tvEmpty;
    private WorkoutDAO workoutDAO;
    private WorkoutAdapter adapter;
    private List<Workout> workoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);

        workoutDAO = new WorkoutDAO(this);

        recyclerWorkouts = findViewById(R.id.recyclerWorkouts);
        tvEmpty = findViewById(R.id.tvEmpty);

        recyclerWorkouts.setLayoutManager(new LinearLayoutManager(this));

        loadWorkouts();
    }

    private void loadWorkouts() {
        workoutDAO.open();
        workoutList = workoutDAO.getAllWorkouts();
        workoutDAO.close();

        if (workoutList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerWorkouts.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            recyclerWorkouts.setVisibility(View.VISIBLE);
        }

        adapter = new WorkoutAdapter(this, workoutList, new WorkoutAdapter.OnWorkoutClickListener() {
            @Override
            public void onWorkoutClick(Workout workout) {
                Intent intent = new Intent(WorkoutListActivity.this, WorkoutDetailActivity.class);
                intent.putExtra("workout_id", workout.getId());
                intent.putExtra("workout_name", workout.getName());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Workout workout) {
                new AlertDialog.Builder(WorkoutListActivity.this)
                        .setTitle("Usuń trening")
                        .setMessage("Czy na pewno chcesz usunąć trening \"" + workout.getName() + "\"?")
                        .setPositiveButton("Usuń", (dialog, which) -> {
                            workoutDAO.open();
                            workoutDAO.deleteWorkout(workout.getId());
                            workoutDAO.close();
                            Toast.makeText(WorkoutListActivity.this, "Trening usunięty", Toast.LENGTH_SHORT).show();
                            loadWorkouts();
                        })
                        .setNegativeButton("Anuluj", null)
                        .show();
            }
        });

        recyclerWorkouts.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWorkouts();
    }
}
