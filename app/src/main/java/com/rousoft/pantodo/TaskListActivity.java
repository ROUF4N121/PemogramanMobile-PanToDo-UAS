package com.rousoft.pantodo; // Ganti dengan package kamu (misal: com.rousoft.pantodo)

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // --- DATA STATIS (Global) ---
    // Agar data tetap ada saat pindah activity
    public static List<Task> globalTasks = new ArrayList<>();
    public static List<Task> globalTrash = new ArrayList<>();

    // --- KOMPONEN UI ---
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private Toolbar toolbar;
    private ExtendedFloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        // 1. SETUP TOOLBAR
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 2. SETUP DRAWER NAVIGATION
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navView = findViewById(R.id.navView);
        navView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // 3. SETUP RECYCLER VIEW
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 4. DATA DUMMY (Jika kosong)
        if (globalTasks.isEmpty() && globalTrash.isEmpty()) {
            globalTasks.add(new Task("Belanja Bulanan", "Beli beras, minyak, gula"));
            globalTasks.add(new Task("Meeting Klien", "Presentasi progres proyek jam 10"));
            globalTasks.add(new Task("Olahraga", "Jogging sore di taman"));
        }

        // 5. SETUP ADAPTER
        setupAdapter();

        // 6. SETUP FAB (TOMBOL TAMBAH)
        fab = findViewById(R.id.fabAddTask);
        fab.setOnClickListener(v -> {
            // Pindah ke halaman Add Task
            startActivityForResult(new Intent(this, AddTaskActivity.class), 100);
        });
    }

    // --- KONFIGURASI ADAPTER & KLIK ---
    private void setupAdapter() {
        // Parameter ke-2 'false' artinya ini BUKAN halaman trash (Checkbox muncul)
        adapter = new TaskAdapter(globalTasks, false, new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onTaskLongClick(int position) {
                // Masuk Mode Seleksi jika belum aktif
                if (!adapter.isSelectionMode) {
                    adapter.isSelectionMode = true;
                    globalTasks.get(position).isSelected = true;
                    adapter.notifyDataSetChanged();
                    updateToolbarSelection();
                }
            }

            @Override
            public void onTaskClick(int position) {
                // Jika sedang Mode Seleksi, klik berfungsi sebagai centang/uncentang
                if (adapter.isSelectionMode) {
                    globalTasks.get(position).isSelected = !globalTasks.get(position).isSelected;
                    adapter.notifyItemChanged(position);
                    updateToolbarSelection();
                } else {
                    // Jika Mode Normal, klik biasa (misal lihat detail)
                    Toast.makeText(TaskListActivity.this, globalTasks.get(position).title, Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    // --- LOGIKA UPDATE TAMPILAN APP BAR ---
    private void updateToolbarSelection() {
        int count = 0;
        for (Task t : globalTasks) {
            if (t.isSelected) count++;
        }

        if (count > 0) {
            // Mode Seleksi Aktif
            getSupportActionBar().setTitle(count + " Selected");
            fab.setVisibility(View.GONE); // Sembunyikan tombol tambah
        } else {
            // Mode Normal
            getSupportActionBar().setTitle(getString(R.string.task_list_title));
            adapter.isSelectionMode = false; // Matikan mode seleksi di adapter
            fab.setVisibility(View.VISIBLE); // Munculkan tombol tambah
        }

        // PENTING: Refresh Menu (Ganti Search <-> Trash)
        invalidateOptionsMenu();
    }

    // --- MENU ATAS (SEARCH / TRASH) ---
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (adapter != null && adapter.isSelectionMode) {
            // Jika Mode Seleksi -> Tampilkan Menu Trash & Select All
            getMenuInflater().inflate(R.menu.menu_selection, menu);
        } else {
            // Jika Mode Normal -> Tampilkan Menu Search
            getMenuInflater().inflate(R.menu.menu_main, menu);

            // Logika Pencarian
            MenuItem searchItem = menu.findItem(R.id.action_search);
            SearchView searchView = (SearchView) searchItem.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) { return false; }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filter(newText);
                    return true;
                }
            });
        }
        return true;
    }

    // --- AKSI TOMBOL MENU ---
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        // 1. SELECT ALL / UNSELECT ALL (Toggle)
        if (id == R.id.action_select_all) {
            boolean isAllSelected = true;
            // Cek apakah semua sudah terpilih?
            for (Task t : globalTasks) {
                if (!t.isSelected) {
                    isAllSelected = false;
                    break;
                }
            }

            // Toggle status
            boolean targetState = !isAllSelected;
            for (Task t : globalTasks) t.isSelected = targetState;

            adapter.notifyDataSetChanged();
            updateToolbarSelection();
            return true;
        }

        // 2. DELETE (PINDAH KE TRASH)
        if (id == R.id.action_delete_selected) {
            List<Task> toRemove = new ArrayList<>();
            for (Task t : globalTasks) {
                if (t.isSelected) toRemove.add(t);
            }

            if (!toRemove.isEmpty()) {
                // Pindahkan ke Global Trash
                for (Task t : toRemove) {
                    t.isSelected = false; // Reset seleksi biar bersih di trash
                    globalTrash.add(t);
                }

                // Hapus dari List Utama
                globalTasks.removeAll(toRemove);

                // Reset Mode Seleksi
                adapter.isSelectionMode = false;
                for(Task t : globalTasks) t.isSelected = false;

                adapter.notifyDataSetChanged();
                updateToolbarSelection();
                Toast.makeText(this, toRemove.size() + " Tasks moved to Trash", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // --- FILTER PENCARIAN ---
    private void filter(String text) {
        List<Task> filtered = new ArrayList<>();
        for (Task item : globalTasks) {
            if (item.title.toLowerCase().contains(text.toLowerCase())) {
                filtered.add(item);
            }
        }
        adapter.filterList(filtered);
    }

    // --- NAVIGASI DRAWER ---
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_trash) {
            startActivity(new Intent(this, TrashActivity.class));
        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(this, SettingActivity.class));
        } else if (id == R.id.nav_feedback) {
            startActivity(new Intent(this, FeedbackActivity.class));
        } else if (id == R.id.nav_logout) {
            showLogoutDialog();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // --- LOGIKA BACK BUTTON ---
    @Override
    public void onBackPressed() {
        if (adapter.isSelectionMode) {
            // Jika sedang seleksi, tombol back hanya membatalkan seleksi
            adapter.isSelectionMode = false;
            for (Task t : globalTasks) t.isSelected = false;
            adapter.notifyDataSetChanged();
            updateToolbarSelection();
        } else if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            // Jika drawer terbuka, tutup dulu
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            // Keluar aplikasi
            super.onBackPressed();
        }
    }

    // --- TERIMA DATA DARI ADD TASK ---
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            // Refresh list setelah tambah data
            adapter.notifyDataSetChanged();
        }
    }

    // --- DIALOG LOGOUT ---
    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.logout_confirm_title))
                .setMessage(getString(R.string.logout_confirm_msg))
                .setPositiveButton(getString(R.string.btn_logout), (dialog, which) -> {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setNegativeButton(getString(R.string.btn_stay), null)
                .show();
    }
}