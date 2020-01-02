package com.example.todolist.di;

import android.app.Application;

import com.example.todolist.ToDoApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                AppModule.class,
                ViewModelFactoryModule.class,
                ActivityBuildersModule.class}
)
public interface AppComponent extends AndroidInjector<ToDoApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}