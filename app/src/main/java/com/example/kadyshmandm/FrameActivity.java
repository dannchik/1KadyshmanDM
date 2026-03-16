package com.example.kadyshmandm;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FrameActivity extends AppCompatActivity {

    private static final String TAG = "FrameActivity_Lifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Активность создана");
        setContentView(R.layout.activity_frame);

        // Кнопка "Готово"
        Button btnDone = findViewById(R.id.btnFrame);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Завершение работы");

                EditText editText = findViewById(R.id.editFrame);
                String text = editText.getText().toString().trim();

                if (text.isEmpty()) {
                    Toast.makeText(FrameActivity.this, "Введите текст!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(FrameActivity.this, "Данные сохранены: " + text, Toast.LENGTH_LONG).show();
                finish();
            }
        });

        // Получаем данные из предыдущей активности (если есть)
        String receivedText = getIntent().getStringExtra("CONSTRAINT_TEXT");
        if (receivedText != null) {
            Log.d(TAG, "Получены данные: " + receivedText);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Активность становится видимой");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Активность активна");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Активность приостановлена");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Активность остановлена");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Активность уничтожается");
    }
}