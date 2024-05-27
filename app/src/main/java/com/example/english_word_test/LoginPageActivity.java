package com.example.english_word_test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPageActivity extends AppCompatActivity {

    TextView textViewRegister;
    EditText editTextUsername, editTextPassword;
    Button buttonLogin, buttonAdminLogin;
    LoginDatabaseHelper loginDatabaseHelper;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonAdminLogin = findViewById(R.id.buttonAdminLogin);
        textViewRegister = findViewById(R.id.textViewRegister);
        loginDatabaseHelper = new LoginDatabaseHelper(this);

        buttonLogin.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (loginDatabaseHelper.checkUser(username, password)) {    // Giriş başarılıysa, StartGameActivity'yi başlat
                Toast.makeText(LoginPageActivity.this, "Giriş Başarılı!", Toast.LENGTH_SHORT).show();

                Intent intent1 = new Intent(LoginPageActivity.this, TestActivity.class);
                intent1.putExtra("username", username);
                startActivity(intent1);

                Intent intent = new Intent(LoginPageActivity.this, StartGameActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            } else {
                Toast.makeText(LoginPageActivity.this, "Kullanıcı adı veya şifre hatalı!", Toast.LENGTH_SHORT).show();
            }
        });

        textViewRegister.setOnClickListener(v -> {   // Kayıt sayfasına geçiş yapılır

            Intent intent = new Intent(LoginPageActivity.this, RegisterPageActivity.class);
            startActivity(intent);
        });

        buttonAdminLogin.setOnClickListener(view -> {
            Intent intent3 = new Intent(LoginPageActivity.this,AdminPageActivity.class);
            startActivity(intent3);
        });
    }
}


