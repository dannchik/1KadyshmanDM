package com.example.kadyshmandm;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomBarActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);

        // Инициализация ActionBar
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("🏠 Главная");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Инициализация BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Загружаем первый фрагмент по умолчанию
        if (savedInstanceState == null) {
            loadFragment(new BottomHomeFragment());
            bottomNavigationView.setSelectedItemId(R.id.nav_bottom_home);
        }

        // 🔹 Обработчик выбора пункта меню
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_bottom_home) {
                    loadFragment(new BottomHomeFragment());
                    if (actionBar != null) {
                        actionBar.setTitle("🏠 Главная");
                    }
                    return true;
                } else if (itemId == R.id.nav_bottom_notifications) {
                    loadFragment(new BottomNotificationsFragment());
                    if (actionBar != null) {
                        actionBar.setTitle("🔔 Уведомления");
                    }
                    return true;
                } else if (itemId == R.id.nav_bottom_settings) {
                    loadFragment(new BottomSettingsFragment());
                    if (actionBar != null) {
                        actionBar.setTitle("⚙️ Настройки");
                    }
                    return true;
                }

                return false;
            }
        });
    }

    // 🔹 Метод загрузки фрагмента
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_bottom, fragment);
        transaction.commit();
    }

    // Обработка кнопки "Назад" в ActionBar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}