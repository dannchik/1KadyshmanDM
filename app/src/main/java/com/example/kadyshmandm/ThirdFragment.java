package com.example.kadyshmandm;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ThirdFragment extends Fragment {

    private int goalCounter = 0;
    private TextView counterText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        TextView title = view.findViewById(R.id.textFragmentTitle);
        title.setText("🎯 Цели");
        counterText = view.findViewById(R.id.counterText);

        Button btnIncrement = view.findViewById(R.id.btnIncrement);
        btnIncrement.setOnClickListener(v -> {
            goalCounter++;
            counterText.setText("Достигнуто целей: " + goalCounter);

            v.animate()
                    .scaleX(0.95f)
                    .scaleY(0.95f)
                    .setDuration(50)
                    .withEndAction(() -> {
                        v.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(50)
                                .start();
                    })
                    .start();
        });

        Button btnFinish = view.findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(v -> {
            Toast.makeText(getContext(),
                    "🎉 Поздравляем! Целей достигнуто: " + goalCounter,
                    Toast.LENGTH_LONG).show();

            view.animate()
                    .alpha(0f)
                    .setDuration(300)
                    .withEndAction(() ->
                            getParentFragmentManager().popBackStack())
                    .start();
        });

        Button btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        return view;
    }
}