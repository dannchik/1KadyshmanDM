package com.example.kadyshmandm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity {

    private ListView listViewCategories;
    private String[] categories = {"🍎 Фрукты", "🥬 Овощи", "🥛 Молочные продукты", "🍞 Хлебобулочные"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        listViewCategories = findViewById(R.id.listViewCategories);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                categories
        );

        listViewCategories.setAdapter(adapter);

        listViewCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categories[position];
                Toast.makeText(CategoryActivity.this,
                        "Выбрана категория: " + selectedCategory,
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CategoryActivity.this, ProductsActivity.class);
                intent.putExtra("CATEGORY_NAME", selectedCategory);
                intent.putExtra("CATEGORY_POSITION", position);
                startActivity(intent);
            }
        });
    }
}