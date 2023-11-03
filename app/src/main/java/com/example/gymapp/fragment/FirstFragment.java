package com.example.gymapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gymapp.R;
import com.example.gymapp.databinding.FragmentFirstBinding;

import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;


public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    //Some how need to extract map of user names and passwords from SignUpFragment
    private final Map<String, String> users = new HashMap<>();

    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        getActivity().setTitle("Sign In"); //idk why this won't change the title
        binding = FragmentFirstBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usernameEditText = view.findViewById(R.id.new_username_entry);
        passwordEditText = view.findViewById(R.id.new_password_entry);
        Button signInButton = view.findViewById(R.id.update_button);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}