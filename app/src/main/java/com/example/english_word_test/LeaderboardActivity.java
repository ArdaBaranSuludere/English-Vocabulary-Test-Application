package com.example.english_word_test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {
    private ListView listView;
    private LoginDatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        listView = findViewById(R.id.list_view);
        databaseHelper = new LoginDatabaseHelper(this);

        List<User> leaderboard = databaseHelper.getLeaderboard();

        // Liderlik tablosunu ListView'da görüntülemek için bir ArrayAdapter kullanalım
        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, leaderboard);
        listView.setAdapter(adapter);
    }
}