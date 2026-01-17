package com.rousoft.pantodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class ForgotPasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // --- UBAH BARIS INI KE XML YANG BARU ---
        setContentView(R.layout.activity_forgot_password);
        // ----------------------------------------

        MaterialButton btnSend = findViewById(R.id.btnSendForgot);
        TextView tvBack = findViewById(R.id.tvBackToLogin);

        // Aksi Tombol Send
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lanjut ke halaman OTP (Slide 5)
                Intent intent = new Intent(ForgotPasswordActivity.this, OtpActivity.class);
                startActivity(intent);
            }
        });

        // Aksi Tombol Back
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kembali ke halaman sebelumnya (Login)
            }
        });
    }
}
