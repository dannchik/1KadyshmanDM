package com.example.kadyshmandm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ConstraintActivity extends AppCompatActivity {

    private static final String TAG = "ConstraintActivity_Lifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Активность создана");
        setContentView(R.layout.activity_constraint);

        Button btnSend = findViewById(R.id.btnConstraint);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Переход к FrameActivity");

                EditText editText = findViewById(R.id.editConstraint);
                String text = editText.getText().toString().trim();

                if (text.isEmpty()) {
                    Toast.makeText(ConstraintActivity.this, "Введите текст!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(ConstraintActivity.this, FrameActivity.class);
                intent.putExtra("CONSTRAINT_TEXT", text);
                startActivity(intent);
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