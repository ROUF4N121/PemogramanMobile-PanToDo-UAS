package com.rousoft.pantodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SuccessResetActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_reset);

        // Saat tombol Back to Login ditekan
        findViewById(R.id.tvBackToLoginFinal).setOnClickListener(v -> {
            Intent intent = new Intent(SuccessResetActivity.this, LoginActivity.class);
            // Hapus semua history activity agar user tidak bisa tekan tombol back HP
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}