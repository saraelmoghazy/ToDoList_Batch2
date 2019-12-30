package com.example.todolist.di;

import com.example.todolist.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {BaseViewModelModule.class,
                    BaseFragmentBuildersModule.class}
    )
    abstract MainActivity contributeMainActivity();
}
