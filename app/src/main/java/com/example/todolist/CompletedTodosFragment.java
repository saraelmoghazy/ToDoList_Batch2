package com.example.todolist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.todolist.adapters.OnCardClickListener;
import com.example.todolist.adapters.TodosAdapter;
import com.example.todolist.databinding.FragmentCompletedTodosBinding;
import com.example.todolist.models.Todo;
import com.example.todolist.utils.NetworkState;
import com.example.todolist.viewmodels.BaseViewModel;
import com.example.todolist.viewmodels.ViewModelProviderFactory;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import retrofit2.Response;

public class CompletedTodosFragment extends DaggerFragment implements
        DialogInterface.OnClickListener,
        OnCardClickListener {
    private static final String TAG = "CompletedTodosFragment";
    private BaseViewModel baseViewModel;
    private FragmentCompletedTodosBinding binding;
    private TodosAdapter adapter;
    private List<Todo> todoList;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Completed Todos");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_completed_todos, container, false);
        todoList = new ArrayList<>();
        baseViewModel = viewModelProviderFactory.create(BaseViewModel.class);
        baseViewModel.getCompletedTodoUseCase();
        observeCompletedTodos();
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    private void observeCompletedTodos() {
        baseViewModel.getNetworkState().observe(getActivity(), networkState -> {
            switch (networkState.status) {
                case LOADING: {
                    isLoading(networkState.status);
                    break;
                }
                case SUCCESS: {
                    isLoading(networkState.status);
                    todoList.addAll((List<Todo>) networkState.data);
                    adapter = new TodosAdapter(this, R.layout.item_todo, todoList);
                    binding.recyclerViewTodos.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.recyclerViewTodos.setAdapter(adapter);
                    break;
                }
                case ERROR: {
                    isLoading(networkState.status);
                    showError();
                    break;
                }
            }
        });
    }

    private void isLoading(NetworkState.Status status) {
        if (status == NetworkState.Status.LOADING) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    private void showError() {
        new MaterialAlertDialogBuilder(getContext())
                .setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again")
                .setPositiveButton("Retry", this)
                .show();
    }

    @Override
    public void onCardClicked(MaterialCardView cardView, int position) {
        Log.d(TAG, "onCardClicked: " + todoList.get(position).toString());
        if (!cardView.isChecked()) {
            cardView.setChecked(true);
            todoList.get(position).setCompleted(false);
            baseViewModel.updateTodoUseCase(todoList.get(position));
            todoList.remove(todoList.get(position));
            Snackbar.make(getView(), "Moved to not completed todos", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        baseViewModel.retry();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                FragmentManager fm = getParentFragmentManager();
                fm.popBackStackImmediate();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
}