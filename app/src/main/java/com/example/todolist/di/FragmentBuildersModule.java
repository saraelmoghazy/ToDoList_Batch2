package com.example.todolist.di;

import com.example.todolist.ToDoListFragment;
import com.example.todolist.CompletedTodosFragment;
import com.example.todolist.AddToDoFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract ToDoListFragment contributeBaseFragment();

    @ContributesAndroidInjector
    abstract AddToDoFragment contributeFullScreenDialogFragment();

    @ContributesAndroidInjector
    abstract CompletedTodosFragment contributeCompletedTodosFragment();
}
