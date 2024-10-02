package com.example.stonks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> productList;
    private Resumen resumen;

    public ProductAdapter(List<Product> productList, Resumen resumen) {
        this.productList = productList;
        this.resumen = resumen;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.textViewProductName.setText(product.getName());
        holder.textViewProductPrice.setText("Precio: $" + product.getPrice());
        holder.textViewProductDate.setText("Fecha y Hora: " + product.getDate());

        holder.buttonEditProduct.setOnClickListener(v -> {
            resumen.showEditDialog(product, position); // Muestra el diálogo de edición
        });

        holder.buttonDeleteProduct.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition(); // Obtiene la posición actual
            if (adapterPosition != RecyclerView.NO_POSITION) {
                resumen.deleteProduct(adapterPosition); // Llama al método de eliminación
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewProductName, textViewProductPrice, textViewProductDate;
        Button buttonEditProduct, buttonDeleteProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewProductPrice = itemView.findViewById(R.id.textViewProductPrice);
            textViewProductDate = itemView.findViewById(R.id.textViewProductDate);
            buttonEditProduct = itemView.findViewById(R.id.buttonEditProduct);
            buttonDeleteProduct = itemView.findViewById(R.id.buttonDeleteProduct);
        }
    }
}

