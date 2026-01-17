package com.rousoft.pantodo;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Tombol Back
        findViewById(R.id.btnBackSetting).setOnClickListener(v -> finish());

        // Aksi Delete Cache (Simulasi)
        LinearLayout btnDeleteCache = findViewById(R.id.btnDeleteCache);
        btnDeleteCache.setOnClickListener(v -> {
            Toast.makeText(this, "Cache Cleared!", Toast.LENGTH_SHORT).show();
        });
    }
}
