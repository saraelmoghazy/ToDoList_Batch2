package com.example.todolist.adapters;

import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;


public class TodoViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "TodoViewHolder";
    private ViewDataBinding binding;

    public TodoViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Object obj) {
        binding.setVariable(BR.obj, obj);
        binding.executePendingBindings();
    }

}

