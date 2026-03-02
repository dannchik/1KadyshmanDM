package com.example.kadyshmandm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayMessageActivity extends AppCompatActivity {

    private static final String TAG = "DisplayMessage_Lifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Вторая активность создана");

        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();

        String name = intent.getStringExtra(MainActivity.EXTRA_NAME);
        String surname = intent.getStringExtra(MainActivity.EXTRA_SURNAME);
        String group = intent.getStringExtra(MainActivity.EXTRA_GROUP);
        String age = intent.getStringExtra(MainActivity.EXTRA_AGE);
        String grade = intent.getStringExtra(MainActivity.EXTRA_GRADE);

        TextView resultText = findViewById(R.id.resultText);

        String result = "ФИО: " + surname + " " + name + "\n" +
                "Группа: " + (group.isEmpty() ? "не указана" : group) + "\n" +
                "Возраст: " + (age.isEmpty() ? "не указан" : age) + "\n" +
                "Желаемая оценка: " + (grade.isEmpty() ? "не указана" : grade);

        resultText.setText(result);

        Toast.makeText(this, "Данные получены!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Вторая активность становится видимой");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Вторая активность активна");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Вторая активность приостановлена");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Вторая активность остановлена");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Вторая активность уничтожается");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: Вторая активность перезапускается");
    }
}