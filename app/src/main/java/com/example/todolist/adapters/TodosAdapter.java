package com.example.todolist.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.todolist.databinding.ItemTodoBinding;
import com.example.todolist.models.Todo;

import java.util.List;

public class TodosAdapter extends SingleLayoutAdapter {
    private static final String TAG = "TodosAdapter";
    private List<Todo> todoList;
    private OnCardClickListener onCardClickListener;

    public TodosAdapter(OnCardClickListener onCardClickListener, int layoutId, List<Todo> todoList) {
        super(layoutId);
        this.todoList = todoList;
        this.onCardClickListener = onCardClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemTodoBinding binding = DataBindingUtil.findBinding(holder.itemView);
        binding.cardViewTodo.setOnClickListener(v -> {
            onCardClickListener.onCardClicked(binding.cardViewTodo, position);
            Log.d(TAG, "onBindViewHolder: click");
        });
        binding.cardViewTodo.setOnLongClickListener(v -> {
            onCardClickListener.onCardClicked(binding.cardViewTodo, position);
            Log.d(TAG, "onBindViewHolder: long click");
            return true;
        });
    }

    @Override
    protected Object getObjForPosition(int position) {
        return todoList.get(position);
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }
}
