package com.example.kadyshmandm;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private com.example.kadyshmandm.ProductAdapter adapter;
    private List<com.example.kadyshmandm.ProductItem> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        loadProductData();

        adapter = new com.example.kadyshmandm.ProductAdapter(productList);
        recyclerView.setAdapter(adapter);
    }

    private void loadProductData() {
        productList.add(new com.example.kadyshmandm.ProductItem("🍎 Яблоко", "Свежее красное яблоко, 1 кг", R.drawable.apple));
        productList.add(new com.example.kadyshmandm.ProductItem("🍌 Банан", "Спелые бананы, 1 кг", R.drawable.banana));
        productList.add(new com.example.kadyshmandm.ProductItem("🍇 Виноград", "Виноград без косточек, 500 г", R.drawable.grape));
        productList.add(new com.example.kadyshmandm.ProductItem("🍊 Апельсин", "Сочные апельсины, 1 кг", R.drawable.orange));
        productList.add(new com.example.kadyshmandm.ProductItem("🍓 Клубника", "Свежая клубника, 250 г", R.drawable.strawberry));
        productList.add(new com.example.kadyshmandm.ProductItem("🥛 Молоко", "Молоко 3.2%, 1 л", R.drawable.milk));
        productList.add(new com.example.kadyshmandm.ProductItem("🧀 Сыр", "Сыр твёрдый, 200 г", R.drawable.cheese));
        productList.add(new com.example.kadyshmandm.ProductItem("🍞 Хлеб", "Хлеб белый, 500 г", R.drawable.bread));
        productList.add(new com.example.kadyshmandm.ProductItem("🥚 Яйца", "Яйца куриные, 10 шт", R.drawable.eggs));
        productList.add(new com.example.kadyshmandm.ProductItem("🍗 Курица", "Филе куриное, 1 кг", R.drawable.chicken));
    }
}