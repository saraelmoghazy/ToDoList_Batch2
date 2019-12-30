package com.example.todolist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.adapters.OnCardClickListener;
import com.example.todolist.adapters.TodosAdapter;
import com.example.todolist.databinding.FragmentBaseBinding;
import com.example.todolist.models.Todo;
import com.example.todolist.utils.NetworkState;
import com.example.todolist.viewmodels.BaseViewModel;
import com.example.todolist.viewmodels.ViewModelProviderFactory;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import retrofit2.Response;

public class BaseFragment extends DaggerFragment implements DialogInterface.OnClickListener, OnCardClickListener {
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
        todoList = new ArrayList<>();
        baseViewModel = viewModelProviderFactory.create(BaseViewModel.class);
        baseViewModel.getNotCompletedTodoUseCase();
        fragmentBaseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, container, false);
        observeTodos();
        fragmentBaseBinding.floatingActionButton.setOnClickListener(v -> {
            FragmentManager fm = getFragmentManager();
            FullScreenDialogFragment dialogFragment = new FullScreenDialogFragment();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.add(android.R.id.content, dialogFragment).commit();
//            NavDirections action = BaseFragmentDirections.actionBaseFragmentToFullScreenDialogFragment();
//            Navigation.findNavController(v).navigate(action);
        });
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
                    Response response = (Response) networkState.data;
                    Log.d(TAG, "onChanged: case success: " + ((Response) networkState.data).body());
                    isLoading(networkState.status);
                    if (response.body() != null) {
                        todoList.addAll((List<Todo>) response.body());
                    }
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
                .setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again")
                .setPositiveButton("Retry", this)
                .show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        baseViewModel.retry();
    }

    @Override
    public void onCardClicked(MaterialCardView materialCardView, int position) {
        Log.d(TAG, "onCardClicked: " + todoList.get(position));
        if (!materialCardView.isChecked()) {
            materialCardView.setChecked(true);
            todoList.get(position).setCompleted(true);
            baseViewModel.updateTodoUseCase(todoList.get(position));
            todoList.remove(todoList.get(position));
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
        }
    };
}