package com.example.kadyshmandm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Activity3Time extends AppCompatActivity {

    private static final String TAG = "Activity3_Lifecycle";

    public static final String EXTRA_SUBJECT = "com.example.kadyshmandm.SUBJECT";
    public static final String RESULT_DAY = "com.example.kadyshmandm.DAY";
    public static final String RESULT_TIME = "com.example.kadyshmandm.TIME";
    public static final String RESULT_COMMENT = "com.example.kadyshmandm.COMMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Activity 3 создана");
        setContentView(R.layout.activity_3_time);

        TextView textSubject = findViewById(R.id.textSubject);
        EditText editDay = findViewById(R.id.editDay);
        EditText editTime = findViewById(R.id.editTime);
        EditText editComment = findViewById(R.id.editComment);
        Button btnOk = findViewById(R.id.btnOk);
        Button btnCancel = findViewById(R.id.btnCancel);

        String subject = getIntent().getStringExtra(EXTRA_SUBJECT);
        textSubject.setText("Предмет: " + subject);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = editDay.getText().toString().trim();
                String time = editTime.getText().toString().trim();
                String comment = editComment.getText().toString().trim();

                if (day.isEmpty() || time.isEmpty()) {
                    Toast.makeText(Activity3Time.this,
                            "Введите день и время!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 🔹 Создаём Intent для возврата данных
                Intent resultIntent = new Intent();
                resultIntent.putExtra(RESULT_DAY, day);
                resultIntent.putExtra(RESULT_TIME, time);
                resultIntent.putExtra(RESULT_COMMENT, comment);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Activity 3 становится видимой");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Activity 3 активна");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Activity 3 приостановлена");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Activity 3 остановлена");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Activity 3 уничтожается");
    }
}