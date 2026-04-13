package com.example.workouttracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttracker.R;
import com.example.workouttracker.models.Exercise;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private Context context;
    private List<Exercise> exercises;
    private OnExerciseDeleteListener listener;

    public interface OnExerciseDeleteListener {
        void onDeleteClick(Exercise exercise);
    }

    public ExerciseAdapter(Context context, List<Exercise> exercises, OnExerciseDeleteListener listener) {
        this.context = context;
        this.exercises = exercises;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);

        holder.tvExerciseName.setText(exercise.getName());
        holder.tvExerciseSets.setText(exercise.getSets() + " serie");
        holder.tvExerciseReps.setText(exercise.getReps() + " powt.");
        holder.tvExerciseWeight.setText(exercise.getWeight() + " kg");

        holder.btnDeleteExercise.setOnClickListener(v -> listener.onDeleteClick(exercise));
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView tvExerciseName, tvExerciseSets, tvExerciseReps, tvExerciseWeight;
        Button btnDeleteExercise;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExerciseName = itemView.findViewById(R.id.tvExerciseName);
            tvExerciseSets = itemView.findViewById(R.id.tvExerciseSets);
            tvExerciseReps = itemView.findViewById(R.id.tvExerciseReps);
            tvExerciseWeight = itemView.findViewById(R.id.tvExerciseWeight);
            btnDeleteExercise = itemView.findViewById(R.id.btnDeleteExercise);
        }
    }
}
