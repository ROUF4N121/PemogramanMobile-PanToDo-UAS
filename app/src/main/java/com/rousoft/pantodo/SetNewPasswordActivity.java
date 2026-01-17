package com.rousoft.pantodo;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SetNewPasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        TextInputEditText etPass = findViewById(R.id.etNewPass);
        TextInputEditText etConfirm = findViewById(R.id.etConfirmNewPass);
        MaterialButton btnReset = findViewById(R.id.btnResetAction);
        TextView tvBack = findViewById(R.id.tvBackToLoginReset);

        // Aksi Reset Password
        btnReset.setOnClickListener(v -> {
            String pass = etPass.getText().toString();
            String confirm = etConfirm.getText().toString();

            if(pass.isEmpty() || confirm.isEmpty()){
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            } else if (!pass.equals(confirm)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                // Sukses -> Pindah ke Halaman Slide 7 (Success Reset)
                startActivity(new Intent(SetNewPasswordActivity.this, SuccessResetActivity.class));
                finish();
            }
        });

        // Back to Login
        tvBack.setOnClickListener(v -> {
            Intent intent = new Intent(SetNewPasswordActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}