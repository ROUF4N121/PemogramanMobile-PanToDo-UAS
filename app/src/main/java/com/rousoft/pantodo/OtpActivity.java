package com.rousoft.pantodo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class OtpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        EditText otp1 = findViewById(R.id.otp1);
        EditText otp2 = findViewById(R.id.otp2);
        EditText otp3 = findViewById(R.id.otp3);
        EditText otp4 = findViewById(R.id.otp4);
        Button btnVerify = findViewById(R.id.btnVerify);
        TextView tvBack = findViewById(R.id.tvBackToLoginOtp);

        // Aksi Tombol Verifikasi
        btnVerify.setOnClickListener(v -> {
            // Gabungkan teks dari 4 kotak
            String code = otp1.getText().toString() + otp2.getText().toString() +
                    otp3.getText().toString() + otp4.getText().toString();

            // Cek Kode Simulasi: Harus "1212"
            if (code.equals("1212")) {
                Toast.makeText(this, "Verified!", Toast.LENGTH_SHORT).show();

                // Lanjut ke Set Password (Slide 6)
                // Pastikan kamu sudah buat activity SetNewPasswordActivity, atau ganti ke MainActivity dulu
                startActivity(new Intent(this, SetNewPasswordActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Wrong Code! Try 1212", Toast.LENGTH_SHORT).show();
            }
        });

        // Aksi Back to Login
        tvBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}