package com.example.todolist;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.todolist.databinding.FragmentDialogFullscreenBinding;
import com.example.todolist.models.Todo;
import com.example.todolist.viewmodels.BaseViewModel;
import com.example.todolist.viewmodels.ViewModelProviderFactory;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.android.support.DaggerDialogFragment;

public class AddToDoFragment extends DaggerDialogFragment {
    private static final String TAG = "FullScreenDialogFragmen";
    private BaseViewModel viewModel;
    private FragmentDialogFullscreenBinding binding;
    private boolean closeButton;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_fullscreen, container, false);
        viewModel = viewModelProviderFactory.create(BaseViewModel.class);
        binding.buttonClose.setOnClickListener(v -> {
            closeButton = true;
            dismiss();
        });
        binding.buttonSave.setOnClickListener(v ->
        {
            String title = binding.editTextTitle.getText().toString();
            String content = binding.editTextContent.getText().toString();
            Todo.Builder builder = new Todo.Builder();
            if (TextUtils.isEmpty(title) && TextUtils.isEmpty(content)) {
                closeButton = true;
                dismiss();
            } else {
                Log.d(TAG, "onCreateView: else");
                closeButton = false;
                if (!TextUtils.isEmpty(title)) {
                    builder.setTitle(title);
                }
                if (!TextUtils.isEmpty(content)) {
                    builder.setContent(content);
                }
                Todo todo = builder.build();
                Log.d(TAG, "onCreateView: todo id: " + todo.getId());
                Log.d(TAG, "onCreateView: todo title: " + todo.getTitle());
                viewModel.postTodoUseCase(todo);
                Snackbar.make(getView(), "todo is saved", Snackbar.LENGTH_SHORT)
                        .setAnchorView(R.id.floating_action_button)
                        .show();
                dismiss();
            }
        });

        return binding.getRoot();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        AlertDialog alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return alertDialog;
    }

    @Override
    public void dismiss() {
        FragmentManager fm = getParentFragmentManager();
        fm.popBackStack("baseFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if (!closeButton) { // save button clicked
            super.dismiss();
            FragmentTransaction ft = fm.beginTransaction().replace(R.id.frame_container, new ToDoListFragment());
            ft.commit();
        } else {
            super.dismiss();
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
}