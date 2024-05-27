package com.example.english_word_test;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private TextView questionTextView;
    private TextView scoreTextView;
    private Button ansAButton;
    private Button ansBButton;
    private Button submitButton;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private String correctAnswer;
    private Button selectedButton;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        questionTextView = findViewById(R.id.question);
        scoreTextView = findViewById(R.id.textViewScore);
        ansAButton = findViewById(R.id.ans_A);
        ansBButton = findViewById(R.id.ans_B);
        submitButton = findViewById(R.id.submit_button);

        // Intent ile gelen verileri alma
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        questions = (List<Question>) intent.getSerializableExtra("questions");

        // İlk soruyu gösterme
        if (questions != null && !questions.isEmpty()) {
            showQuestion(currentQuestionIndex);
        }

        // Cevap seçenekleri için tıklama olayları
        ansAButton.setOnClickListener(v -> {
            selectedButton = ansAButton;
            selectedButton.setBackgroundColor(Color.GRAY);
            ansBButton.setBackgroundColor(Color.WHITE);
        });

        ansBButton.setOnClickListener(v -> {
            selectedButton = ansBButton;
            selectedButton.setBackgroundColor(Color.GRAY);
            ansAButton.setBackgroundColor(Color.WHITE);
        });

        // Submit butonu için tıklama olayı
        submitButton.setOnClickListener(v -> {
            if (selectedButton == null) {
                Toast.makeText(TestActivity.this, "Please choose an option", Toast.LENGTH_SHORT).show();
                return;
            }

            String selectedAnswer = selectedButton.getText().toString();

            if (selectedAnswer.equals(correctAnswer)) {
                selectedButton.setBackgroundColor(Color.GREEN);
                score++;
                playSound(R.raw.at_right_answer);
            } else {
                selectedButton.setBackgroundColor(Color.RED);
                playSound(R.raw.at_wrong_answer);
                if (ansAButton.getText().toString().equals(correctAnswer)) {
                    ansAButton.setBackgroundColor(Color.GREEN);
                } else {
                    ansBButton.setBackgroundColor(Color.GREEN);
                }
            }

            scoreTextView.setText("Score: " + score + "/" + (currentQuestionIndex+1)); // Skor / toplam soru sayısı olarak güncellendi

            // Belirli bir süre sonra bir sonraki soruyu göster
            new Handler().postDelayed(this::nextQuestion, 2000);
        });
    }

    private void showQuestion(int index) {
        if (index < questions.size()) {
            Question question = questions.get(index);
            questionTextView.setText(question.getQuestionText());
            ansAButton.setText(question.getOptionA());
            ansBButton.setText(question.getOptionB());
            correctAnswer = question.getCorrectAnswer();

            // Buton renklerini temizle
            ansAButton.setBackgroundColor(Color.WHITE);
            ansBButton.setBackgroundColor(Color.WHITE);
            selectedButton = null;
        } else {
            Toast.makeText(this, "Quiz Finished! Your score: " + score, Toast.LENGTH_LONG).show();
            // Quiz bittiğinde yapılacak işlemler
            finishQuiz();
        }
    }

    private void nextQuestion() {
        currentQuestionIndex++;
        showQuestion(currentQuestionIndex);
    }

    private void finishQuiz() {
        // Quiz bittiğinde skor kaydetme işlemleri
        Intent intent = new Intent(TestActivity.this, StartGameActivity.class);
        startActivity(intent);
    }

    private void playSound(int soundResource) {
        releaseMediaPlayer();
        mediaPlayer = MediaPlayer.create(TestActivity.this, soundResource);
        mediaPlayer.start();
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}