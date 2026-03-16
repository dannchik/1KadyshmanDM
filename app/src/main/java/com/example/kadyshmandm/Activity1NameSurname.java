package com.example.kadyshmandm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Activity1NameSurname extends AppCompatActivity {

    private static final String TAG = "Activity1_Lifecycle";

    public static final String EXTRA_NAME = "com.example.kadyshmandm.NAME";
    public static final String EXTRA_SURNAME = "com.example.kadyshmandm.SURNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Activity 1 создана");
        setContentView(R.layout.activity_1_name_surname);

        Button btnNext = findViewById(R.id.btnNextToActivity2);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editName = findViewById(R.id.editName);
                EditText editSurname = findViewById(R.id.editSurname);

                String name = editName.getText().toString().trim();
                String surname = editSurname.getText().toString().trim();

                if (name.isEmpty() || surname.isEmpty()) {
                    Toast.makeText(Activity1NameSurname.this,
                            "Введите имя и фамилию!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 🔹 Переход к Activity 2
                Intent intent = new Intent(Activity1NameSurname.this, Activity2Subject.class);
                intent.putExtra(EXTRA_NAME, name);
                intent.putExtra(EXTRA_SURNAME, surname);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Activity 1 становится видимой");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Activity 1 активна");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Activity 1 приостановлена");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Activity 1 остановлена");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Activity 1 уничтожается");
    }
}