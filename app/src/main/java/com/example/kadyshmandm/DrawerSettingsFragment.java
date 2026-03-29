package com.example.kadyshmandm;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DrawerSettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer_settings, container, false);

        TextView title = view.findViewById(R.id.fragmentTitle);
        title.setText("⚙️ Настройки");

        Button btnOpenActivity = view.findViewById(R.id.btnOpenSecondActivity);
        btnOpenActivity.setOnClickListener(v -> {
            Toast.makeText(getContext(), "🚀 Переход ко второй Activity", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), BottomBarActivity.class);
            startActivity(intent);
        });

        return view;
    }
}