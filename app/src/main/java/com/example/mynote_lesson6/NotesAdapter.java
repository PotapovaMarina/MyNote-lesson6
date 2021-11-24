package com.example.mynote_lesson6;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private List<NoteEntity> data = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private int position;

    public void setData(List<NoteEntity> notes) {
        data = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(parent, onItemClickListener);
    }

    public void setOnItemClickListener(OnItemClickListener OnItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(data.get(position));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}