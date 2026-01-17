package com.rousoft.pantodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private OnTaskClickListener listener;
    public boolean isSelectionMode = false;
    private boolean isTrashMode; // Penanda apakah ini halaman Trash

    public interface OnTaskClickListener {
        void onTaskLongClick(int position);
        void onTaskClick(int position);
    }

    // Constructor Diupdate: Tambah parameter boolean isTrashMode
    public TaskAdapter(List<Task> taskList, boolean isTrashMode, OnTaskClickListener listener) {
        this.taskList = taskList;
        this.isTrashMode = isTrashMode; // Simpan status
        this.listener = listener;
    }

    public void filterList(List<Task> filteredList) {
        this.taskList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.tvTitle.setText(task.title);
        holder.tvDesc.setText(task.description);

        // LOGIKA BARU: Jika Trash Mode, Sembunyikan Checkbox
        if (isTrashMode) {
            holder.cbDone.setVisibility(View.GONE);
        } else {
            holder.cbDone.setVisibility(View.VISIBLE);
            holder.cbDone.setChecked(task.isDone);
        }

        // Background color logic (Selected vs Normal)
        if (task.isSelected) {
            holder.itemView.setBackgroundResource(R.color.selected_blue);
        } else {
            holder.itemView.setBackgroundResource(R.color.white);
        }

        holder.itemView.setOnLongClickListener(v -> {
            listener.onTaskLongClick(position);
            return true;
        });

        holder.itemView.setOnClickListener(v -> {
            listener.onTaskClick(position);
        });

        holder.cbDone.setOnCheckedChangeListener((buttonView, isChecked) -> task.isDone = isChecked);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc;
        CheckBox cbDone;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.itemTitle);
            tvDesc = itemView.findViewById(R.id.itemDesc);
            cbDone = itemView.findViewById(R.id.itemCheckBox);
        }
    }
}