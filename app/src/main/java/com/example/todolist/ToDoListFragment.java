package com.example.todolist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.adapters.OnCardClickListener;
import com.example.todolist.adapters.TodosAdapter;
import com.example.todolist.databinding.FragmentBaseBinding;
import com.example.todolist.models.StatusToDoResponse;
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

public class ToDoListFragment extends DaggerFragment implements DialogInterface.OnClickListener, OnCardClickListener {
    private static final String TAG = "BaseFragment";
    private BaseViewModel baseViewModel;
    private FragmentBaseBinding fragmentBaseBinding;
    private TodosAdapter adapter;
    private List<Todo> todoList;
    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Todo List");
        todoList = new ArrayList<>();
        baseViewModel = viewModelProviderFactory.create(BaseViewModel.class);
        fragmentBaseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, container, false);
        observeTodos();
        fragmentBaseBinding.floatingActionButton.setOnClickListener(v -> {
            FragmentManager fm = getParentFragmentManager();
            AddToDoFragment dialogFragment = new AddToDoFragment();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.add(android.R.id.content, dialogFragment).addToBackStack("baseFragment").commit();
        });
        setHasOptionsMenu(true);
        return fragmentBaseBinding.getRoot();
    }

    private void observeTodos() {
        baseViewModel.getNetworkState().observe(getActivity(), networkState -> {
            switch (networkState.status) {
                case LOADING: {
                    isLoading(networkState.status);
                    break;
                }
                case SUCCESS: {
                    isLoading(networkState.status);
                    if (networkState.data instanceof StatusToDoResponse)
                        Toast.makeText(getActivity(), "complete", Toast.LENGTH_LONG).show();
                    else
                        todoList.addAll((List<Todo>) networkState.data);
                    new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(fragmentBaseBinding.recyclerViewTodos);
                    adapter = new TodosAdapter(this, R.layout.item_todo, todoList);
                    fragmentBaseBinding.recyclerViewTodos.setLayoutManager(new LinearLayoutManager(getContext()));
                    fragmentBaseBinding.recyclerViewTodos.setAdapter(adapter);
                    break;
                }
                case ERROR: {
                    isLoading(networkState.status);
                    Log.d(TAG, "observeTodos: error: " + networkState.message);
                    showError();
                    break;
                }
            }
        });
    }

    private void isLoading(NetworkState.Status status) {
        if (status == NetworkState.Status.LOADING) {
            fragmentBaseBinding.progressBar.setVisibility(View.VISIBLE);
        } else {
            fragmentBaseBinding.progressBar.setVisibility(View.GONE);
        }
    }

    private void showError() {
        new MaterialAlertDialogBuilder(getContext())
                .setTitle(R.string.no_internet_connection)
                .setMessage(R.string.check_internet_connection)
                .setPositiveButton(R.string.retry, this)
                .show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        baseViewModel.retry();
    }

    @Override
    public void onCardClicked(MaterialCardView materialCardView, int position) {
        Log.d(TAG, "onCardClicked: " + todoList.get(position));
        Todo todo = todoList.get(position);
        if (!materialCardView.isChecked()) {
            materialCardView.setChecked(true);
            todo.setCompleted(true);
            baseViewModel.updateTodoUseCase(todo);
            todoList.remove(todo);
            Snackbar.make(getView(), R.string.moved_to_completed, Snackbar.LENGTH_SHORT)
                    .setAnchorView(fragmentBaseBinding.floatingActionButton).show();
        }
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Todo todo = todoList.get(viewHolder.getAdapterPosition());
            todoList.remove(todo);
            baseViewModel.deleteTodoUseCase(todo);
            Snackbar.make(getView(), R.string.todo_deleted, Snackbar.LENGTH_LONG)
                    .setAnchorView(fragmentBaseBinding.floatingActionButton)
                    .setAction(R.string.undo, v -> {
                        todoList.add(todo);
                        baseViewModel.postTodoUseCase(todo);
                    }).show();
        }
    };

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.completed_todos: {
                FragmentManager fm = getParentFragmentManager();
                FragmentTransaction ft = fm.beginTransaction().replace(R.id.frame_container, new CompletedTodosFragment());
                ft.addToBackStack(null);
                ft.commit();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

}