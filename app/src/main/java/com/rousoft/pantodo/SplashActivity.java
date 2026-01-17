package com.rousoft.pantodo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SplashActivity extends AppCompatActivity {

    private TextView tvLoading;
    private int dotCount = 0;
    private Handler handler;
    private Runnable runnable;

    // Total durasi splash screen (misal 3 detik)
    private static final int SPLASH_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tvLoading = findViewById(R.id.tvLoading);
        handler = new Handler(Looper.getMainLooper());

        // 1. Logika untuk animasi titik "Loading..."
        startLoadingAnimation();

        // 2. Logika untuk pindah ke Main Activity setelah waktu habis
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                stopLoadingAnimation(); // Hentikan animasi

                Intent intent = new Intent(SplashActivity.this, WelcomeSelectActivity.class);
                startActivity(intent);

                // Tambahkan animasi transisi (Fade in/out standar)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                finish(); // Tutup SplashActivity agar tidak bisa kembali
            }
        }, SPLASH_DELAY);
    }

    private void startLoadingAnimation() {
        runnable = new Runnable() {
            @Override
            public void run() {
                String baseText = getString(R.string.loading);
                StringBuilder sb = new StringBuilder(baseText);

                // Menambah titik sesuai dotCount (0 sampai 3)
                for (int i = 0; i < dotCount; i++) {
                    sb.append(".");
                }

                tvLoading.setText(sb.toString());

                // Reset jika sudah 3 titik, jika belum tambah 1
                if (dotCount == 3) {
                    dotCount = 0;
                } else {
                    dotCount++;
                }

                // Ulangi setiap 500ms (0.5 detik)
                handler.postDelayed(this, 500);
            }
        };
        handler.post(runnable);
    }

    private void stopLoadingAnimation() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
