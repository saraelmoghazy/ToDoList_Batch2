package com.example.todolist.adapters;

public abstract class SingleLayoutAdapter extends MyBaseAdapter {
    private final int layoutId;

    public SingleLayoutAdapter(int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return layoutId;
    }
}
