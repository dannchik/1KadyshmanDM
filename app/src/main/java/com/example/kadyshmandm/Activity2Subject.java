package com.example.kadyshmandm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class Activity2Subject extends AppCompatActivity {

    private static final String TAG = "Activity2_Lifecycle";

    public static final String EXTRA_NAME = "com.example.kadyshmandm.NAME";
    public static final String EXTRA_SURNAME = "com.example.kadyshmandm.SURNAME";
    public static final String EXTRA_SUBJECT = "com.example.kadyshmandm.SUBJECT";

    // 🔹 Ключи для результата из Activity 3
    public static final String RESULT_DAY = "com.example.kadyshmandm.DAY";
    public static final String RESULT_TIME = "com.example.kadyshmandm.TIME";
    public static final String RESULT_COMMENT = "com.example.kadyshmandm.COMMENT";

    private TextView textNameSurname;
    private EditText editSubject;
    private TextView textResult;

    // 🔹 ActivityResultLauncher для запуска Activity 3
    private ActivityResultLauncher<Intent> launchActivity3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Activity 2 создана");
        setContentView(R.layout.activity_2_subject);

        textNameSurname = findViewById(R.id.textNameSurname);
        editSubject = findViewById(R.id.editSubject);
        textResult = findViewById(R.id.textResult);
        Button btnToActivity3 = findViewById(R.id.btnToActivity3);

        String name = getIntent().getStringExtra(EXTRA_NAME);
        String surname = getIntent().getStringExtra(EXTRA_SURNAME);
        textNameSurname.setText("Студент: " + surname + " " + name);

        launchActivity3 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.d(TAG, "onActivityResult: Получен результат от Activity 3");

                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                String day = data.getStringExtra(RESULT_DAY);
                                String time = data.getStringExtra(RESULT_TIME);
                                String comment = data.getStringExtra(RESULT_COMMENT);

                                String resultText = "✅ Данные получены:\n" +
                                        "День: " + day + "\n" +
                                        "Время: " + time + "\n" +
                                        "Комментарий: " + comment;

                                textResult.setText(resultText);
                                Toast.makeText(Activity2Subject.this,
                                        "Время занятия успешно передано!", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            textResult.setText("❌ Результат отменён");
                            Toast.makeText(Activity2Subject.this,
                                    "Результат отменён", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        btnToActivity3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = editSubject.getText().toString().trim();
                if (subject.isEmpty()) {
                    Toast.makeText(Activity2Subject.this,
                            "Введите предмет!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(Activity2Subject.this, Activity3Time.class);
                intent.putExtra(EXTRA_SUBJECT, subject);

                launchActivity3.launch(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Activity 2 становится видимой");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Activity 2 активна");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Activity 2 приостановлена");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Activity 2 остановлена");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Activity 2 уничтожается");
    }
}