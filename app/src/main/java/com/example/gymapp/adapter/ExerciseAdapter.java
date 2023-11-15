package com.example.gymapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CheckBox;
import com.bumptech.glide.Glide;


import androidx.recyclerview.widget.RecyclerView;

import com.example.gymapp.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Exercise;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private List<Exercise> exercises;
    private Set<Exercise> selectedExercises;

    public ExerciseAdapter(List<Exercise> exercises) {
        this.exercises = exercises;
        this.selectedExercises = new HashSet<>();
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.textViewExerciseName.setText(exercise.getName());
        holder.textViewMuscleGroup.setText(exercise.getMuscle()); // Set muscle group text

        Glide.with(holder.itemView.getContext())
                .asGif()
                .load(exercise.getPreviewSrc())
                .into(holder.imageViewPreview);

        holder.checkBoxExercise.setOnCheckedChangeListener(null);
        holder.checkBoxExercise.setChecked(selectedExercises.contains(exercise));
        holder.checkBoxExercise.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedExercises.add(exercise);
            } else {
                selectedExercises.remove(exercise);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView textViewExerciseName, textViewMuscleGroup;
        ImageView imageViewPreview;
        CheckBox checkBoxExercise;

        public ExerciseViewHolder(View itemView) {
            super(itemView);
            textViewExerciseName = itemView.findViewById(R.id.textViewExerciseName);
            textViewMuscleGroup = itemView.findViewById(R.id.textViewMuscleGroup); // Muscle group view
            imageViewPreview = itemView.findViewById(R.id.imageViewPreview);
            checkBoxExercise = itemView.findViewById(R.id.checkBoxExercise);
        }
    }


    public void updateExercises(List<Exercise> newExercises) {
        exercises.clear();
        exercises.addAll(newExercises);
        notifyDataSetChanged();
    }

    // Method to retrieve selected exercises
    public Set<Exercise> getSelectedExercises() {
        return selectedExercises;
    }
}

