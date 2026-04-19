package com.example.kadyshmandm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {

    public interface CustomDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, String text);
    }

    private CustomDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (CustomDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CustomDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_custom, null);

        EditText editInput = view.findViewById(R.id.editDialogInput);

        builder.setView(view)
                .setTitle("Введите имя")
                .setMessage("Введите ваше имя для подтверждения:")
                .setPositiveButton("OK", (dialog, which) -> {
                    String text = editInput.getText().toString().trim();
                    if (!text.isEmpty()) {
                        listener.onDialogPositiveClick(MyDialogFragment.this, text);
                    } else {
                        Toast.makeText(getContext(), "Введите текст!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());

        return builder.create();
    }
}