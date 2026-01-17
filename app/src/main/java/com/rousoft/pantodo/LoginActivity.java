package com.rousoft.pantodo; // Pastikan nama package ini sesuai dengan folder projekmu

// --- BAGIAN INI YANG HILANG DI KODEMU (IMPORT) ---
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
// --------------------------------------------------

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Deklarasi View
        TextInputEditText etEmail = findViewById(R.id.etEmail);
        TextInputEditText etPass = findViewById(R.id.etPassword);
        MaterialButton btnLogin = findViewById(R.id.btnLoginAction);
        TextView tvForgot = findViewById(R.id.tvForgot);
        TextView tvGoToSignUp = findViewById(R.id.tvGoToSignUp);

        // 1. LOGIKA TOMBOL LOGIN
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();

                // Simulasi Login Sederhana
                if(email.equals("topan67@gmail.com")) {
                    // Jika benar, masuk ke MainActivity (Halaman Welcome Back)
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    // Clear task agar user tidak bisa back ke halaman login
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    // Jika salah
                    Toast.makeText(LoginActivity.this, "Email Salah! Gunakan topan67@gmail.com", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 2. LOGIKA LUPA PASSWORD -> Ke ForgotPasswordActivity
        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pastikan kamu sudah membuat ForgotPasswordActivity.java
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

        // 3. LOGIKA BELUM PUNYA AKUN -> Ke SignUpActivity
        tvGoToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
            }
        });
    }
}