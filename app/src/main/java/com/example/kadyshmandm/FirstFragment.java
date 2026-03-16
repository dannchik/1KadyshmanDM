package com.example.kadyshmandm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FirstFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        TextView title = view.findViewById(R.id.textFragmentTitle);
        title.setText("📋 Список задач");

        CheckBox task1 = view.findViewById(R.id.task1);
        CheckBox task2 = view.findViewById(R.id.task2);
        CheckBox task3 = view.findViewById(R.id.task3);

        Button btnToSecond = view.findViewById(R.id.btnToSecond);
        btnToSecond.setOnClickListener(v -> {

            int completed = (task1.isChecked() ? 1 : 0) +
                    (task2.isChecked() ? 1 : 0) +
                    (task3.isChecked() ? 1 : 0);

            Toast.makeText(getContext(),
                    "Выполнено: " + completed + "/3 → Переход",
                    Toast.LENGTH_SHORT).show();

            getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out)
                    .replace(R.id.fragment_container, new SecondFragment())
                    .addToBackStack("first_to_second")
                    .commit();
        });

        return view;
    }
}