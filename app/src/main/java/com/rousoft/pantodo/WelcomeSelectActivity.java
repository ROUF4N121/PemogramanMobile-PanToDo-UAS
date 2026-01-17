package com.rousoft.pantodo;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
// --------------------------------------

public class WelcomeSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_select);

        // 1. LOGIKA TOMBOL GUEST -> Langsung ke Main Menu (Welcome Back)
        findViewById(R.id.tvGuest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke MainActivity
                startActivity(new Intent(WelcomeSelectActivity.this, MainActivity.class));
            }
        });

        // 2. LOGIKA TOMBOL LOGIN -> Ke Halaman Login
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeSelectActivity.this, LoginActivity.class));
            }
        });

        // 3. LOGIKA TOMBOL SIGN UP -> Ke Halaman Sign Up
        findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pastikan SignUpActivity sudah dibuat. Jika belum, baris ini akan merah.
                // Jika merah, buat dulu file SignUpActivity.java seperti langkah sebelumnya.
                startActivity(new Intent(WelcomeSelectActivity.this, SignUpActivity.class));
            }
        });

        // 4. LOGIKA BENDERA (Otomatis ganti gambar jika bahasa HP Indonesia)
        ImageView ivFlag = findViewById(R.id.ivFlagSelect);
        String languageCode = Locale.getDefault().getLanguage(); // Dapatkan kode bahasa HP

        // Kode "in" atau "id" biasanya dipakai untuk Indonesia
        if (languageCode.equals("id") || languageCode.equals("in")) {
            ivFlag.setImageResource(R.drawable.ic_flag_id);
        } else {
            ivFlag.setImageResource(R.drawable.ic_flag_us);
        }
    }
}