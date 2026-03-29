package com.example.kadyshmandm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);

        EditText editComment = findViewById(R.id.editComment);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = editComment.getText().toString().trim();
                if (comment.isEmpty()) {
                    Toast.makeText(ScrollViewActivity.this,
                            "Введите комментарий!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ScrollViewActivity.this,
                            "Отправлено: " + comment, Toast.LENGTH_LONG).show();
                    editComment.setText("");
                }
            }
        });
    }
}