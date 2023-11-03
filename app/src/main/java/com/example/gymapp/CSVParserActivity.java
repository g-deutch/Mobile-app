package com.example.gymapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class CSVParserActivity extends AppCompatActivity {

    private static final String TAG = "CSVParserActivity";
    private static final String PREFS_NAME = "MyAppPrefs";
    private static final String KEY_CSV_PARSED = "csv_parsed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check SharedPreferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getBoolean(KEY_CSV_PARSED, false)) {
            navigateToMain();
            return;
        }

        setContentView(R.layout.activity_csv_parser);

        parseCSVAndUploadToFirestore();
    }

    private void parseCSVAndUploadToFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        try {
            InputStream is = getAssets().open("exercise_list.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            reader.readLine(); // Skip headers

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");

                if (tokens.length != 7) {
                    Log.e("CSVParserActivity", "Skipping line due to incorrect number of values: " + line);
                    continue; // skip this line and go to the next one
                }

                Map<String, Object> exercise = new HashMap<>();
                exercise.put("id", tokens[0]);
                exercise.put("name", tokens[1]);
                exercise.put("equipment", tokens[2]);
                exercise.put("level", tokens[3]);
                exercise.put("muscle", tokens[4]);
                exercise.put("previewSrc", tokens[5]);
                exercise.put("videoLink", tokens[6]);

                db.collection("exercises").add(exercise)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "Document added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
            }

            reader.close();
            is.close();

            // Mark CSV as parsed in SharedPreferences
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(KEY_CSV_PARSED, true);
            editor.apply();

            navigateToMain();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}


