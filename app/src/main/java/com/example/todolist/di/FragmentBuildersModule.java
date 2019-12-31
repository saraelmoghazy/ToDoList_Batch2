package com.example.todolist.di;

import com.example.todolist.BaseFragment;
import com.example.todolist.CompletedTodosFragment;
import com.example.todolist.FullScreenDialogFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract BaseFragment contributeBaseFragment();

    @ContributesAndroidInjector
    abstract FullScreenDialogFragment contributeFullScreenDialogFragment();

    @ContributesAndroidInjector
    abstract CompletedTodosFragment contributeCompletedTodosFragment();
}
