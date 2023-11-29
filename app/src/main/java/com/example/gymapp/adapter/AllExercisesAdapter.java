package com.example.gymapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gymapp.R;
import com.example.gymapp.activity.EditExerciseActivity;
import com.example.gymapp.activity.AllExercisesActivity;


import java.util.List;
import java.util.Map;

public class AllExercisesAdapter extends RecyclerView.Adapter<AllExercisesAdapter.ExerciseViewHolder> {

    private final List<Map<String, Object>> exercises;
    private final LayoutInflater inflater;
    private final Context context;
    private final String userDocument;

    public AllExercisesAdapter(Context context, List<Map<String, Object>> exercises, String userDocument) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.exercises = exercises;
        this.userDocument = userDocument;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.exercise_item, parent, false);
        return new ExerciseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Map<String, Object> currentExercise = exercises.get(position);
        holder.exerciseButton.setText((String) currentExercise.get("name"));
        holder.exerciseButton.setOnClickListener(v -> {
            String exerciseDocument = (String) currentExercise.get("document");
            Intent intent = new Intent(context, EditExerciseActivity.class);
            intent.putExtra("userDocument", AllExercisesActivity.userDocument);
            intent.putExtra("workoutDocument", exerciseDocument);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        final Button exerciseButton;

        ExerciseViewHolder(View itemView) {
            super(itemView);
            exerciseButton = itemView.findViewById(R.id.exercise_button);
        }
    }
}
