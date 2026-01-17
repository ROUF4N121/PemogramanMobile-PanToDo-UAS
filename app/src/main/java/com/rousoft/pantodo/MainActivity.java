package com.rousoft.pantodo; // Sesuaikan dengan package kamu

import android.content.Intent; // PENTING: Import Intent untuk pindah halaman
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton btnContinue = findViewById(R.id.btnContinue);
        ImageView ivFlag = findViewById(R.id.ivFlag);

        // --- Logika Cek Bahasa (Tetap Sama) ---
        String languageCode = Locale.getDefault().getLanguage();

        if (languageCode.equals("in") || languageCode.equals("id")) {
            ivFlag.setImageResource(R.drawable.ic_flag_id);
        } else {
            ivFlag.setImageResource(R.drawable.ic_flag_us);
        }
        // ---------------------------------------

        // --- UPDATE DI SINI ---
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat Intent untuk pindah ke TaskListActivity (Halaman Utama / Slide 2)
                Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
                startActivity(intent);

                // Opsional: Tambahkan finish() jika kamu ingin menutup halaman Welcome ini
                // agar saat user tekan tombol Back, aplikasi langsung keluar (bukan balik ke welcome).
                // finish();
            }
        });
    }
}