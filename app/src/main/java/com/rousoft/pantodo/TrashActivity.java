package com.rousoft.pantodo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TrashActivity extends AppCompatActivity {

    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);

        findViewById(R.id.btnBackTrash).setOnClickListener(v -> finish());

        RecyclerView rvTrash = findViewById(R.id.rvTrash);
        rvTrash.setLayoutManager(new LinearLayoutManager(this));

        // PENTING: Gunakan 'globalTrash' sebagai sumber data
        // PENTING: Parameter kedua 'true' artinya ini mode Trash (Checkbox hilang)
        adapter = new TaskAdapter(TaskListActivity.globalTrash, true, new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onTaskLongClick(int position) {
                // Opsional: Bisa tambah fitur hapus permanen disini
            }

            @Override
            public void onTaskClick(int position) {
                showRestoreDialog(position);
            }
        });

        rvTrash.setAdapter(adapter);
    }

    // Fitur Tambahan: Dialog untuk Mengembalikan (Restore) atau Hapus Permanen
    private void showRestoreDialog(int position) {
        Task task = TaskListActivity.globalTrash.get(position);

        new AlertDialog.Builder(this)
                .setTitle("Manage Trash")
                .setMessage("What do you want to do with '" + task.title + "'?")
                .setPositiveButton("Restore", (dialog, which) -> {
                    // Kembalikan ke Task List Utama
                    TaskListActivity.globalTrash.remove(task);
                    TaskListActivity.globalTasks.add(task);

                    adapter.notifyItemRemoved(position);
                    Toast.makeText(this, "Task Restored!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Delete Forever", (dialog, which) -> {
                    // Hapus Selamanya
                    TaskListActivity.globalTrash.remove(task);
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(this, "Deleted Permanently", Toast.LENGTH_SHORT).show();
                })
                .setNeutralButton("Cancel", null)
                .show();
    }
}