package com.example.kadyshmandm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SecondFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        TextView title = view.findViewById(R.id.textFragmentTitle);
        title.setText("✅ Выполнено");

        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        TextView progressText = view.findViewById(R.id.progressText);

        int progress = 66; // 2 из 3 задач
        progressBar.setProgress(progress);
        progressText.setText(progress + "% задач выполнено");

        Button btnToThird = view.findViewById(R.id.btnToThird);
        btnToThird.setOnClickListener(v -> {
            Toast.makeText(getContext(),
                    "🎯 Переход к целям...",
                    Toast.LENGTH_SHORT).show();

            getParentFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right)
                    .replace(R.id.fragment_container, new ThirdFragment())
                    .addToBackStack("second_to_third")
                    .commit();
        });

        Button btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        return view;
    }
}