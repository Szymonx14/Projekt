package com.example.workouttracker.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttracker.R;
import com.example.workouttracker.models.Workout;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    private Context context;
    private List<Workout> workouts;
    private OnWorkoutClickListener listener;

    public interface OnWorkoutClickListener {
        void onWorkoutClick(Workout workout);
        void onDeleteClick(Workout workout);
    }

    public WorkoutAdapter(Context context, List<Workout> workouts, OnWorkoutClickListener listener) {
        this.context = context;
        this.workouts = workouts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_workout, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Workout workout = workouts.get(position);

        holder.tvWorkoutName.setText(workout.getName());
        holder.tvWorkoutType.setText(workout.getType());
        holder.tvWorkoutDate.setText(workout.getDate());
        holder.tvWorkoutDuration.setText(workout.getDuration() + " min");

        // Kolor paska zależny od typu treningu
        switch (workout.getType()) {
            case "Cardio":
                holder.viewTypeColor.setBackgroundColor(Color.parseColor("#4CAF50"));
                break;
            case "Rozciąganie":
                holder.viewTypeColor.setBackgroundColor(Color.parseColor("#2196F3"));
                break;
            default:
                holder.viewTypeColor.setBackgroundColor(Color.parseColor("#FF6B35"));
                break;
        }

        holder.itemView.setOnClickListener(v -> listener.onWorkoutClick(workout));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(workout));
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        TextView tvWorkoutName, tvWorkoutType, tvWorkoutDate, tvWorkoutDuration;
        View viewTypeColor;
        Button btnDelete;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWorkoutName = itemView.findViewById(R.id.tvWorkoutName);
            tvWorkoutType = itemView.findViewById(R.id.tvWorkoutType);
            tvWorkoutDate = itemView.findViewById(R.id.tvWorkoutDate);
            tvWorkoutDuration = itemView.findViewById(R.id.tvWorkoutDuration);
            viewTypeColor = itemView.findViewById(R.id.viewTypeColor);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
