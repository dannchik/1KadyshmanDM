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

        findViewById(R.id.btnScrollView).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ScrollViewActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnCategoryList).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnRecyclerView).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnSpinner).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SpinnerActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnNavigationDrawer).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, com.example.kadyshmandm.NavigationDrawerActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnBottomBar).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BottomBarActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnSendDeclarative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageDeclarative(v);
            }
        });

        findViewById(R.id.btnToLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Переход к LinearActivity");
                Intent intent = new Intent(MainActivity.this, LinearActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnStartChain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity1NameSurname.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnStaticFragment).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StaticFragmentActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnDynamicFragment).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DynamicFragmentActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnContainerViewFragment).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContainerViewFragmentActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: Активность перезапускается после остановки");
    }

    public void sendMessageProgrammatic(View view) {
        Log.d(TAG, "sendMessageProgrammatic: Запуск перехода с объектом Student");

        Intent intent = new Intent(this, DisplayMessageActivity.class);

        EditText editName = findViewById(R.id.editName);
        EditText editSurname = findViewById(R.id.editSurname);
        EditText editGroup = findViewById(R.id.editGroup);
        EditText editAge = findViewById(R.id.editAge);
        EditText editGrade = findViewById(R.id.editGrade);

        Student student = new Student(
                editName.getText().toString().trim(),
                editSurname.getText().toString().trim(),
                editGroup.getText().toString().trim(),
                editAge.getText().toString().trim(),
                editGrade.getText().toString().trim()
        );

        intent.putExtra("STUDENT_OBJECT", student);

        startActivity(intent);
    }



    public void sendMessageDeclarative(View view) {
        Log.d(TAG, "sendMessageDeclarative: Запуск перехода с объектом Student");

        Intent intent = new Intent(this, DisplayMessageActivity.class);

        EditText editName = findViewById(R.id.editName);
        EditText editSurname = findViewById(R.id.editSurname);
        EditText editGroup = findViewById(R.id.editGroup);
        EditText editAge = findViewById(R.id.editAge);
        EditText editGrade = findViewById(R.id.editGrade);

        Student student = new Student(
                editName.getText().toString().trim(),
                editSurname.getText().toString().trim(),
                editGroup.getText().toString().trim(),
                editAge.getText().toString().trim(),
                editGrade.getText().toString().trim()
        );
        intent.putExtra("STUDENT_OBJECT", student);

        findViewById(R.id.btnStartChain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity1NameSurname.class);
                startActivity(intent);
            }
        });

        startActivity(intent);
    }
}