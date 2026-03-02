package com.example.kadyshmandm;

import static com.example.kadyshmandm.R.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity_Lifecycle";

    public static final String EXTRA_NAME = "com.example.kadyshmandm.NAME";
    public static final String EXTRA_SURNAME = "com.example.kadyshmandm.SURNAME";
    public static final String EXTRA_GROUP = "com.example.kadyshmandm.GROUP";
    public static final String EXTRA_AGE = "com.example.kadyshmandm.AGE";
    public static final String EXTRA_GRADE = "com.example.kadyshmandm.GRADE";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Активность создана");

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnSendDeclarative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageDeclarative(v);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Активность становится видимой");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Активность активна и готова к взаимодействию");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Активность приостановлена");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Активность остановлена и не видна");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Активность уничтожается");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: Активность перезапускается после остановки");
    }

    public void sendMessageProgrammatic(View view) {
        Log.d(TAG, "sendMessageProgrammatic: Запуск перехода программно");

        Intent intent = new Intent(this, DisplayMessageActivity.class);

        EditText editName = findViewById(R.id.editName);
        EditText editSurname = findViewById(R.id.editSurname);
        EditText editGroup = findViewById(R.id.editGroup);
        EditText editAge = findViewById(R.id.editAge);
        EditText editGrade = findViewById(R.id.editGrade);

        String name = editName.getText().toString().trim();
        String surname = editSurname.getText().toString().trim();
        String group = editGroup.getText().toString().trim();
        String age = editAge.getText().toString().trim();
        String grade = editGrade.getText().toString().trim();

        if (name.isEmpty() || surname.isEmpty()) {
            Toast.makeText(this, "Введите имя и фамилию!", Toast.LENGTH_SHORT).show();
            return;
        }

        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_SURNAME, surname);
        intent.putExtra(EXTRA_GROUP, group);
        intent.putExtra(EXTRA_AGE, age);
        intent.putExtra(EXTRA_GRADE, grade);

        startActivity(intent);
    }

    public void sendMessageDeclarative(View view) {
        Log.d(TAG, "sendMessageDeclarative: Запуск перехода декларативно");

        Intent intent = new Intent(this, DisplayMessageActivity.class);

        EditText editName = findViewById(R.id.editName);
        EditText editSurname = findViewById(R.id.editSurname);
        EditText editGroup = findViewById(R.id.editGroup);
        EditText editAge = findViewById(R.id.editAge);
        EditText editGrade = findViewById(R.id.editGrade);

        intent.putExtra(EXTRA_NAME, editName.getText().toString().trim());
        intent.putExtra(EXTRA_SURNAME, editSurname.getText().toString().trim());
        intent.putExtra(EXTRA_GROUP, editGroup.getText().toString().trim());
        intent.putExtra(EXTRA_AGE, editAge.getText().toString().trim());
        intent.putExtra(EXTRA_GRADE, editGrade.getText().toString().trim());

        startActivity(intent);
    }
}