package com.example.gymapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymapp.databinding.FragmentMyWorkoutsBinding;
import com.example.gymapp.databinding.FragmentSecondBinding;
import com.example.gymapp.databinding.FragmentSettingsBinding;
import com.example.gymapp.databinding.FragmentSignUpBinding;
import com.example.gymapp.databinding.FragmentWorkoutsBinding;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;




public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    private static Map<String, String> users = new HashMap<>();
    String username;
    String password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find UI elements by their IDs

    }

/*
    private FragmentSignUpBinding binding;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.backButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SignUpFragment.this)
                        .navigate(R.id.action_SignUpFragment_to_FirstFragment);
            }
        });
    } */
}