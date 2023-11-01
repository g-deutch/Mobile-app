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

        usernameEditText = view.findViewById(R.id.username_entry);
        passwordEditText = view.findViewById(R.id.password_entry);
        Button signInButton = view.findViewById(R.id.sign_in_button);

        binding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                //line below allows sign in with no user or password
                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);

                //logic below requires valid user and password
                /*
               if (users.containsKey(username) && users.get(username).equals(password)) {
                    Toast.makeText(getActivity(), "Sign in successful!", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_SecondFragment);
                } else {
                    Toast.makeText(getActivity(), "Invalid username or password.", Toast.LENGTH_SHORT).show();
                }
                */
            }
        });
        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SignUpFragment);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}