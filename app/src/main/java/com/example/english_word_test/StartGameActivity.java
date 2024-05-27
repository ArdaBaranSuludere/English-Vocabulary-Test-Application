package com.example.english_word_test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartGameActivity extends AppCompatActivity {

    public Button button_showleaderboard;
    private Map<String, List<Question>> questionBank;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startgame);

        Intent intent_username = getIntent();
        username = intent_username.getStringExtra("username"); // Kullanıcı adını alın

        // Kategorileri ve soruları tanımla
        questionBank = new HashMap<>();
        questionBank.put("Spor", createCategory1Questions());
        questionBank.put("Mutfak", createCategory2Questions());
        questionBank.put("Business", createCategory3Questions());
        questionBank.put("Photography", createCategory4Questions());

        // Butonları tanımla ve tıklama olaylarını dinle
        findViewById(R.id.button_category1).setOnClickListener(view -> startQuiz("Spor"));
        findViewById(R.id.button_category2).setOnClickListener(view -> startQuiz("Mutfak"));
        findViewById(R.id.button_category3).setOnClickListener(view -> startQuiz("Business"));
        findViewById(R.id.button_category4).setOnClickListener(view -> startQuiz("Photography"));

        button_showleaderboard = findViewById(R.id.button_showleaderboard);
        button_showleaderboard.setOnClickListener(view -> showLeaderboard());
    }

    private void showLeaderboard() {
        // Liderlik tablosunu göstermek için bir intent oluştur
        Intent leaderboardIntent = new Intent(this, LeaderboardActivity.class);
        startActivity(leaderboardIntent);
    }

    private void startQuiz(String category) {
        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra("category", category);
        intent.putExtra("username", username); // Kullanıcı adını intent'e ekleyin
        // List<Question>'ı doğrudan intent'e ekleyin
        intent.putExtra("questions", (Serializable) questionBank.get(category));
        startActivity(intent);
    }

    // Spor kategorisi için sorular
    private List<Question> createCategory1Questions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What do you call the game where you hit a small ball into a hole with a club?",
                "Tennis", "Golf", "Golf"));
        questions.add(new Question("What do you call the object you throw to someone who tries to catch it?",
                "Football", "Baseball", "Baseball"));
        questions.add(new Question("Which sport involves riding waves on a board?",
                "Swimming", "Surfing", "Surfing"));
        questions.add(new Question("What do you call the flat piece of equipment used to play hockey?",
                "Bat", "Stick", "Stick"));
        questions.add(new Question("Which sport involves hitting a small, round ball into a net with a racket?",
                "Basketball", "Tennis", "Tennis"));
        questions.add(new Question("What do you call the place where you go to swim and play in the water?",
                "Pool", "Court", "Pool"));
        questions.add(new Question("Which sport involves hitting a ball over a net with a racket?",
                "Baseball", "Tennis", "Tennis"));
        questions.add(new Question("What do you call the round object used to play basketball?",
                "Ball", "Bat", "Ball"));
        questions.add(new Question("Which sport involves kicking a ball into a net??",
                "Soccer", "Golf", "Soccer"));
        questions.add(new Question("What do you call the game where players hit a shuttlecock over a net with a racket?",
                "Badminton", "Volleyball", "Badminton"));

        return questions;
    }

    // Mutfak kategorisi için sorular
    private List<Question> createCategory2Questions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What do you call the device used to heat food quickly?",
                "Toaster", "Blender", "Toaster"));
        questions.add(new Question("What do you call the device used to cook food by circulating hot air around it?",
                "Oven", "Microwave", "Oven"));
        questions.add(new Question("What do you call the small room in which food is cooked?",
                "Pantry", "Kitchen", "Kitchen"));
        questions.add(new Question("What do you call the appliance used to keep food cold?",
                "Oven", "Refrigerator", "Refrigerator"));
        questions.add(new Question("What do you call the device used to mix, crush, or puree food?",
                "Grill", "Blender", "Blender"));
        questions.add(new Question("What do you call the device used to boil water?",
                "Kettle", "Microwave", "Kettle"));
        questions.add(new Question("What do you call the small tool used to peel fruits and vegetables?",
                "Knife", "Peeler", "Peeler"));
        questions.add(new Question("What do you call the container used to store food?",
                "Bowl", "Fridge", "Bowl"));
        questions.add(new Question("What do you call the appliance used to cook food quickly using direct heat?",
                "Stove", "Dishwasher", "Stove"));
        questions.add(new Question("What do you call the appliance used to keep food warm?",
                "Freezer", "Slow cooker", "Slow cooker"));
        return questions;
    }

    // Business kategorisi için sorular
    private List<Question> createCategory3Questions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What do you call a person who starts their own business?",
                "Entrepreneur", "Employee", "Entrepreneur"));
        questions.add(new Question("What do you call the money paid by a borrower to a lender for the use of their money?",
                "Salary", "Interest", "Interest"));
        questions.add(new Question("What do you call a place where goods are bought and sold?",
                "Market", "Bank", "Market"));
        questions.add(new Question("What do you call a company owned by a group of people who buy shares in it?",
                "Corporation", "Government", "Corporation"));
        questions.add(new Question("What do you call the activity of buying and selling goods and services?",
                "Trade", "Travel", "Trade"));
        questions.add(new Question("What do you call the process of bringing goods into a country from abroad?",
                "Export", "Import", "Import"));
        questions.add(new Question("What do you call the money you pay to buy goods and services?",
                "Price", "Tax", "Price"));
        questions.add(new Question("What do you call the person who buys goods and services?",
                "Consumer", "Producer", "Consumer"));
        questions.add(new Question("What do you call the study of how money is made and used?",
                "Economics", "Biology", "Economics"));
        questions.add(new Question("What do you call a written promise to repay a debt?",
                "Loan", "Credit", "Loan"));
        return questions;
    }

    // Photography kategorisi için sorular
    private List<Question> createCategory4Questions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What do you call the device used to take photographs?",
                "Camera", "Computer", "Camera"));
        questions.add(new Question("What do you call the opening in a camera that lets light in?",
                "Shutter", "Aperture", "Aperture"));
        questions.add(new Question("What do you call the practice of taking pictures?",
                "Photography", "Sculpture", "Photography"));
        questions.add(new Question("What do you call a photograph taken from above?",
                "Portrait", "Aerial shot", "Aerial shot"));
        questions.add(new Question("What do you call the device used to support a camera and keep it steady?",
                "Tripod", "Monopod", "Tripod"));
        questions.add(new Question("What do you call the art of taking photographs with a drone?",
                "Droneography", "Cinematography", "Droneography"));
        questions.add(new Question("What do you call the adjustable opening in a camera lens?",
                "Aperture", "Exposure", "Aperture"));
        questions.add(new Question("What do you call a photograph that is produced quickly and inexpensively?",
                "Digital photo", "Instant photo", "Instant photo"));
        questions.add(new Question("What do you call the process of producing a photograph from a negative?",
                "Printing", "Developing", "Developing"));
        questions.add(new Question("What do you call a camera that takes pictures continuously?",
                "Digital camera", "Action camera", "Action camera"));
        return questions;
    }
}