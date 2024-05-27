package com.example.english_word_test;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Map<String, List<Question>> questionBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionBank = new HashMap<>();
        // Initialize question bank from a data source if necessary

        EditText editTextQuestion = findViewById(R.id.editTextQuestion);
        EditText editTextOption1 = findViewById(R.id.editTextOption1);
        EditText editTextOption2 = findViewById(R.id.editTextOption2);
        EditText editTextCorrectAnswer = findViewById(R.id.editTextCorrectAnswer);
        Spinner spinnerCategory = findViewById(R.id.spinnerCategory);
        Button buttonAddQuestion = findViewById(R.id.buttonAddQuestion);

        buttonAddQuestion.setOnClickListener(view -> {
            String questionText = editTextQuestion.getText().toString();
            String option1 = editTextOption1.getText().toString();
            String option2 = editTextOption2.getText().toString();
            String correctAnswer = editTextCorrectAnswer.getText().toString();
            String category = spinnerCategory.getSelectedItem().toString();

            Question newQuestion = new Question(questionText, option1, option2, correctAnswer);
            if (!questionBank.containsKey(category)) {
                questionBank.put(category, new ArrayList<>());
            }
            questionBank.get(category).add(newQuestion);

            // Optionally, save the updated question bank to persistent storage
        });
    }
}
