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
    private TextView questionTextView, scoreTextView;
    private Button ansAButton, ansBButton, submitButton, selectedButton;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private String correctAnswer;
    private MediaPlayer mediaPlayer;
    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        questionTextView = findViewById(R.id.question);
        scoreTextView = findViewById(R.id.textViewScore);
        ansAButton = findViewById(R.id.ans_A);
        ansBButton = findViewById(R.id.ans_B);
        submitButton = findViewById(R.id.submit_button);

        Intent intent_username = getIntent();
        username = intent_username.getStringExtra("username");

        // Intent ile gelen verileri alma
        Intent intent_category = getIntent();
        String category = intent_category.getStringExtra("category");
        questions = (List<Question>) intent_category.getSerializableExtra("questions");

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

            scoreTextView.setText("Score: " + score + "/" + (currentQuestionIndex + 1)); // Skor / toplam soru sayısı olarak güncellendi

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
            // Sorular bittiğinde, kullanıcının skorunu veritabanına kaydet
            try {
                LoginDatabaseHelper loginDbHelper = new LoginDatabaseHelper(this);
                if (username != null) {
                    if (correctAnswer != null && !correctAnswer.isEmpty()) {
                        // Doğru cevap sayısını kaydet ve mesajı göster
                        loginDbHelper.updateScore(username, score);
                        Toast.makeText(TestActivity.this, "Doğrular kaydedildi: " + score,  Toast.LENGTH_SHORT).show();
                    } else {
                        // Doğru soru sayısını boş olarak yazdır
                        Toast.makeText(TestActivity.this, "Doğru soru sayısı: " + score,  Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Kullanıcı adı boş ise, bu durumu ele al
                    Toast.makeText(TestActivity.this, "Kullanıcı adı boş: ",  Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(TestActivity.this, "Doğrular kaydedilemedi" , Toast.LENGTH_SHORT).show();
                // Burada hatanın nasıl ele alınacağına ilişkin bir kod bloğu yazabilirsiniz.
            }
            // Toast.makeText(TestActivity.this, "Sorular bitti! Skorunuz kaydedildi.", Toast.LENGTH_SHORT).show(); //
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
        intent.putExtra("username", username);
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