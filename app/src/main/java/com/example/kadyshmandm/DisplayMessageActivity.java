package com.example.kadyshmandm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.example.kadyshmandm.Student;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayMessageActivity extends AppCompatActivity {

    private static final String TAG = "DisplayMessage_Lifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Вторая активность создана");

        setContentView(R.layout.activity_display_message);

        Student student = (Student) getIntent().getSerializableExtra("STUDENT_OBJECT");

        TextView resultText = findViewById(R.id.resultText);

        if (student != null) {

            resultText.setText(student.getFullInfo());
            Toast.makeText(this, "Данные получены: " + student.getName(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Получен студент: " + student.getName() + " " + student.getSurname());
        } else {
            resultText.setText("❌ Ошибка: данные не получены");
            Log.e(TAG, "onCreate: Объект Student равен null!");
        }
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