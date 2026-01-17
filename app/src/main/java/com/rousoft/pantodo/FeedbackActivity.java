package com.rousoft.pantodo;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FeedbackActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        // Tombol Back
        findViewById(R.id.btnBackFeedback).setOnClickListener(v -> finish());

        // Tombol Send
        findViewById(R.id.btnSendFeedback).setOnClickListener(v -> {
            // Simulasi kirim feedback
            Toast.makeText(this, "Feedback sent! Thank you.", Toast.LENGTH_SHORT).show();
            finish(); // Tutup halaman setelah kirim
        });
    }
}
