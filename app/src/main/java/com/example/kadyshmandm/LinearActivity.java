package com.example.kadyshmandm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LinearActivity extends AppCompatActivity {

    private static final String TAG = "LinearActivity_Lifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Активность создана");
        setContentView(R.layout.activity_linear);

        Button btnNext = findViewById(R.id.btnLinearNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Переход к RelativeActivity");
                Intent intent = new Intent(LinearActivity.this, RelativeActivity.class);
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