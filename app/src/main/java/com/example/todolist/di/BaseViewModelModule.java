package com.example.todolist.di;

import androidx.lifecycle.ViewModel;

import com.example.todolist.viewmodels.BaseViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class BaseViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(BaseViewModel.class)
    public abstract ViewModel bindBaseViewModel(BaseViewModel viewModel);
}
