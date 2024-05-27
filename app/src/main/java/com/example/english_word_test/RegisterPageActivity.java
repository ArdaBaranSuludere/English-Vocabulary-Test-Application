package com.example.english_word_test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterPageActivity extends AppCompatActivity {

    TextView textViewLogin;
    EditText editTextUsername, editTextPassword;
    Button buttonRegister, buttonAdminLogin;
    LoginDatabaseHelper loginDatabaseHelper;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_register);

        editTextUsername = findViewById(R.id.editTextUsername2);
        editTextPassword = findViewById(R.id.editTextPassword2);
        buttonRegister = findViewById(R.id.buttonLogin2);
        buttonAdminLogin = findViewById(R.id.buttonAdminLogin);
        textViewLogin = findViewById(R.id.textViewLogin);
        loginDatabaseHelper = new LoginDatabaseHelper(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                long result = loginDatabaseHelper.addUser(username, password);
                if (result != -1) {
                    Toast.makeText(RegisterPageActivity.this, "Kayıt Başarılı!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterPageActivity.this, "Kayıt Başarısız!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewLogin.setOnClickListener(v -> { // Giriş sayfasına geri dönülür
            finish(); // Bu aktiviteyi kapat
        });
    }
}

