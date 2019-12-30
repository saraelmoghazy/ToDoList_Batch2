package com.example.todolist;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.todolist.viewmodels.BaseViewModel;
import com.example.todolist.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {
    private static final String TAG = "MainActivity";
    private BaseViewModel viewModel;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_container, new BaseFragment(), "BaseFragment");
        fragmentTransaction.commit();

    }

//    private boolean isNetworkConnected() {
//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
//    }
}