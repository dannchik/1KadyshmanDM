package com.example.kadyshmandm;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    private ListView listViewProducts;
    private TextView textCategory;
    private EditText editProduct;
    private Button btnAdd, btnRemove;
    private ArrayAdapter<String> adapter;
    private List<String> productsList;
    private String categoryName;
    private int categoryPosition;

    // Данные для каждой категории
    private String[][] categoryData = {
            {"Яблоко", "Груша", "Банан", "Апельсин"},           // Фрукты
            {"Помидор", "Огурец", "Картофель", "Морковь"},      // Овощи
            {"Молоко", "Кефир", "Творог", "Сыр"},               // Молочные
            {"Хлеб", "Батон", "Булка", "Лаваш"}                 // Хлебобулочные
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        categoryName = getIntent().getStringExtra("CATEGORY_NAME");
        categoryPosition = getIntent().getIntExtra("CATEGORY_POSITION", 0);

        textCategory = findViewById(R.id.textCategory);
        listViewProducts = findViewById(R.id.listViewProducts);
        editProduct = findViewById(R.id.editProduct);
        btnAdd = findViewById(R.id.btnAdd);
        btnRemove = findViewById(R.id.btnRemove);

        textCategory.setText("📦 Категория: " + categoryName);

        productsList = new ArrayList<>();
        for (String item : categoryData[categoryPosition]) {
            productsList.add(item);
        }

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                productsList
        );

        listViewProducts.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            if (categoryPosition != 0) {
                Toast.makeText(this,
                        "❌ Добавление доступно только для категории «Фрукты»",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            String newProduct = editProduct.getText().toString().trim();
            if (newProduct.isEmpty()) {
                Toast.makeText(this, "Введите название товара!", Toast.LENGTH_SHORT).show();
                return;
            }

            productsList.add(newProduct);
            adapter.notifyDataSetChanged();
            editProduct.setText("");
            Toast.makeText(this, "✅ Товар добавлен: " + newProduct, Toast.LENGTH_SHORT).show();
        });

        btnRemove.setOnClickListener(v -> {
            if (categoryPosition != 0) {
                Toast.makeText(this,
                        "❌ Удаление доступно только для категории «Фрукты»",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (productsList.isEmpty()) {
                Toast.makeText(this, "Список пуст!", Toast.LENGTH_SHORT).show();
                return;
            }

            String removed = productsList.remove(productsList.size() - 1);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "❌ Удалён: " + removed, Toast.LENGTH_SHORT).show();
        });

        listViewProducts.setOnItemClickListener((parent, view, position, id) -> {
            String selectedProduct = productsList.get(position);
            Toast.makeText(this, "🛒 Выбрано: " + selectedProduct, Toast.LENGTH_SHORT).show();
        });
    }
}