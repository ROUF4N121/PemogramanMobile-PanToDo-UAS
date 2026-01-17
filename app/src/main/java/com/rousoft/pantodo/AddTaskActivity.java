package com.rousoft.pantodo;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        EditText etTitle = findViewById(R.id.etTitle);
        EditText etDesc = findViewById(R.id.etDesc);

        findViewById(R.id.btnBackAdd).setOnClickListener(v -> finish());

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            String title = etTitle.getText().toString();
            String desc = etDesc.getText().toString();

            if (title.isEmpty()) {
                Toast.makeText(this, "Title Required!", Toast.LENGTH_SHORT).show();
            } else {
                // Simpan ke List Global
                TaskListActivity.globalTasks.add(new Task(title, desc));
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
