package com.example.gymapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymapp.databinding.FragmentSecondBinding;
import com.example.gymapp.databinding.FragmentWorkoutsBinding;


public class WorkoutsFragment extends Fragment {



    private FragmentWorkoutsBinding binding;

    public WorkoutsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWorkoutsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.viewWorkoutsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(WorkoutsFragment.this)
                        .navigate(R.id.action_WorkoutsFragment_to_MyWorkoutsFragment);
            }
        });
        binding.settingsWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(WorkoutsFragment.this)
                        .navigate(R.id.action_WorkoutsFragment_to_SettingsFragment);
            }
        });
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(WorkoutsFragment.this)
                        .navigate(R.id.action_WorkoutsFragment_to_SecondFragment);
            }
        });
    }
}