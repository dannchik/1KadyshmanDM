package com.example.kadyshmandm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<com.example.kadyshmandm.ProductItem> productList;

    public ProductAdapter(List<com.example.kadyshmandm.ProductItem> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        com.example.kadyshmandm.ProductItem item = productList.get(position);
        holder.textViewName.setText(item.getName());
        holder.textViewDescription.setText(item.getDescription());
        holder.imageView.setImageResource(item.getImageResId());

        // Обработка клика по элементу
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(v.getContext(),
                    "🛒 Выбрано: " + item.getName(),
                    Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // ViewHolder для оптимизации производительности
    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewDescription;
        ImageView imageView;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textProductName);
            textViewDescription = itemView.findViewById(R.id.textProductDescription);
            imageView = itemView.findViewById(R.id.imageProduct);
        }
    }
}