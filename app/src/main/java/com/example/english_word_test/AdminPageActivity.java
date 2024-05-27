package com.example.english_word_test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminPageActivity extends AppCompatActivity {
    EditText editTextAdminName,editTextAdminPassword;
    Button buttonAdminLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        buttonAdminLogin = findViewById(R.id.buttonAdminLogin);
        editTextAdminName = findViewById(R.id.editTextAdminName);
        editTextAdminPassword = findViewById(R.id.editTextAdminPassword);

        buttonAdminLogin.setOnClickListener(view -> {
            String adminName = editTextAdminName.getText().toString().trim();
            String adminPassword = editTextAdminPassword.getText().toString().trim();

            if(adminName.equals("arda") && adminPassword.equals("arda12345")) {
                 Intent intent = new Intent(AdminPageActivity.this, MainActivity.class);
                 startActivity(intent);

                Toast.makeText(AdminPageActivity.this, "Giriş başarılı!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AdminPageActivity.this, "Kullanıcı adı veya şifre yanlış!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}