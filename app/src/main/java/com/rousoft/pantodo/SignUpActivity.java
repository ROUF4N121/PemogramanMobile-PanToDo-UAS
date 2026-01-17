package com.rousoft.pantodo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextInputEditText etEmail = findViewById(R.id.etEmailSign);
        TextInputEditText etPass = findViewById(R.id.etPassSign);
        TextInputEditText etConfirm = findViewById(R.id.etConfirmPass);
        MaterialButton btnSignUp = findViewById(R.id.btnSignUpAction);
        TextView tvGoToLogin = findViewById(R.id.tvGoToLogin);

        // Aksi Tombol Sign Up
        btnSignUp.setOnClickListener(v -> {
            String pass = etPass.getText().toString();
            String confirm = etConfirm.getText().toString();

            // Cek sederhana: Password harus sama dengan Confirm Password
            if (pass.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (!pass.equals(confirm)) {
                Toast.makeText(this, "Password does not match!", Toast.LENGTH_SHORT).show();
            } else {
                // Jika sukses, pindah ke Login (Simulasi)
                Toast.makeText(this, "Account Created!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        });

        // Aksi teks "Already have account? Login"
        tvGoToLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
