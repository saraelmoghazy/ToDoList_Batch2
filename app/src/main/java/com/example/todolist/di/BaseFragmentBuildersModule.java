package com.example.todolist.di;

import com.example.todolist.BaseFragment;
import com.example.todolist.FullScreenDialogFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BaseFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract BaseFragment contributeBaseFragment();

    @ContributesAndroidInjector
    abstract FullScreenDialogFragment contributeFullScreenDialogFragment();
}
