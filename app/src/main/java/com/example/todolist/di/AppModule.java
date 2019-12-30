package com.example.todolist.di;

import com.example.todolist.network.TodoApi;
import com.example.todolist.repository.Repository;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.response.RxErrorHandlingCallAdapterFactory;
import com.example.todolist.utils.Constants;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    static TodoApi provideTodoAPi(Retrofit retrofit) {
        return retrofit.create(TodoApi.class);
    }

    @Provides
    static Repository provideRepository(TodoApi todoApi) {
        return new TodoRepository(todoApi);
    }

    @Provides
    @Named("executor_thread")
    static Scheduler provideExecutorThread() {
        return Schedulers.io();
    }

    @Provides
    @Named("ui_thread")
    static Scheduler provideUiThread() {
        return AndroidSchedulers.mainThread();
    }
}