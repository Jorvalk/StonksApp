package com.example.stonks;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Resumen extends AppCompatActivity {

    private EditText editTextProduct, editTextPrice;
    private Button buttonSubmit, buttonBack;
    private RecyclerView recyclerViewHistory;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resumen);

        // Configurar padding para evitar superposición con las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar los componentes de la interfaz
        editTextProduct = findViewById(R.id.editTextProduct);
        editTextPrice = findViewById(R.id.editTextPrice);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonBack = findViewById(R.id.buttonBack);
        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);

        // Inicializar la base de datos
        databaseHelper = new DatabaseHelper(this);

        // Inicializar la lista y el adaptador del RecyclerView
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList, this);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHistory.setAdapter(productAdapter);

        // Cargar productos desde la base de datos
        loadProducts();

        // Configurar el botón para agregar productos
        buttonSubmit.setOnClickListener(v -> addProduct());

        // Función para regresar a la actividad MainActivity
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(Resumen.this, Main.class);
            startActivity(intent);
            finish();
        });
    }

    private void addProduct() {
        String productName = editTextProduct.getText().toString();
        String priceString = editTextPrice.getText().toString();

        if (!productName.isEmpty() && !priceString.isEmpty()) {
            double price = Double.parseDouble(priceString);
            String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());

            // Crear un nuevo producto
            Product product = new Product(0, productName, price, currentDate);
            long id = databaseHelper.insertProduct(product); // Asegúrate de que este método exista
            product.setId((int) id); // Establece el ID del producto insertado

            productList.add(product);
            productAdapter.notifyItemInserted(productList.size() - 1);
            editTextProduct.setText("");
            editTextPrice.setText("");
        } else {
            Toast.makeText(this, "Por favor, ingresa un producto y un precio.", Toast.LENGTH_SHORT).show();
        }
    }




    private void loadProducts() {
        // Limpiar la lista antes de cargar productos
        productList.clear();

        // Cargar productos desde la base de datos
        Cursor cursor = databaseHelper.getAllProducts();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Obtén los índices de las columnas
                int idIndex = cursor.getColumnIndex("id"); // Asegúrate de que la columna ID esté disponible
                int nameIndex = cursor.getColumnIndex("product_name");
                int priceIndex = cursor.getColumnIndex("product_price");
                int dateIndex = cursor.getColumnIndex("product_date");

                // Verifica si los índices son válidos
                if (idIndex != -1 && nameIndex != -1 && priceIndex != -1 && dateIndex != -1) {
                    int id = cursor.getInt(idIndex); // Obtén el ID
                    String name = cursor.getString(nameIndex);
                    double price = cursor.getDouble(priceIndex);
                    String date = cursor.getString(dateIndex);
                    Product product = new Product(id, name, price, date);
                    productList.add(product);
                } else {
                    Log.e("Resumen", "Error: columna no encontrada");
                }
            }
            cursor.close();
        }
        productAdapter.notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
    }


    public void showEditDialog(Product product, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Producto");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_edit_product, (ViewGroup) findViewById(android.R.id.content), false);
        EditText editTextProduct = viewInflated.findViewById(R.id.editTextProduct);
        EditText editTextPrice = viewInflated.findViewById(R.id.editTextPrice);

        editTextProduct.setText(product.getName());
        editTextPrice.setText(String.valueOf(product.getPrice()));

        builder.setView(viewInflated);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String updatedName = editTextProduct.getText().toString();
            double updatedPrice = Double.parseDouble(editTextPrice.getText().toString());
            // Actualiza el producto en la lista y en la base de datos
            productList.get(position).setName(updatedName);
            productList.get(position).setPrice(updatedPrice);
            databaseHelper.updateProduct(product); // Método para actualizar en la base de datos
            productAdapter.notifyItemChanged(position);
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    public void deleteProduct(int position) {
        // Verifica que la posición sea válida
        if (position >= 0 && position < productList.size()) {
            Product product = productList.get(position);
            databaseHelper.deleteProduct(product.getId()); // Eliminar de la base de datos
            productList.remove(position); // Eliminar de la lista
            productAdapter.notifyItemRemoved(position); // Notificar al adaptador
        } else {
            // Manejar el caso en que la posición no es válida
            Log.e("Resumen", "Intento de eliminar producto con posición inválida: " + position);
        }
    }



}
