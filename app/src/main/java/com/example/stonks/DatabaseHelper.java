package com.example.stonks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.stonks.Product;

// Clase para gestionar la base de datos SQLite
public class DatabaseHelper extends SQLiteOpenHelper {
    // Nombre y versión de la base de datos
    private static final String DATABASE_NAME = "Resumen.db"; // Nombre del archivo de la base de datos
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla y columnas de la base de datos
    private static final String TABLE_NAME = "products"; // Nombre de la tabla
    private static final String COLUMN_ID = "id"; // Columna para el ID del producto (clave primaria)
    private static final String COLUMN_NAME = "product_name"; // Columna para el nombre del producto
    private static final String COLUMN_PRICE = "product_price"; // Columna para el precio del producto

    // Constructor de la clase, inicializa el helper de base de datos
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Método que se ejecuta la primera vez que se crea la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Define la estructura de la tabla 'products' con ID, nombre, precio y fecha
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE products (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," + // ID autoincremental para cada producto
                "product_name TEXT," +
                "product_price REAL," +
                "product_date TEXT" +
                ")";
        db.execSQL(CREATE_PRODUCTS_TABLE); // Ejecuta el SQL para crear la tabla
    }

    // Método que se ejecuta cuando la base de datos necesita actualizarse
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Elimina la tabla existente si ya existe
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db); // Crea una nueva tabla
    }

    // Método para insertar un nuevo producto en la base de datos
    public long insertProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase(); // Obtiene base de datos en modo escritura
        ContentValues contentValues = new ContentValues(); // Contenedor de valores para las columnas
        contentValues.put(COLUMN_NAME, product.getName()); // Asigna nombre del producto
        contentValues.put(COLUMN_PRICE, product.getPrice()); // Asigna precio del producto
        contentValues.put("product_date", product.getDate()); // Asigna fecha del producto

        // Inserta el producto en la tabla y devuelve el ID del nuevo registro
        long id = db.insert(TABLE_NAME, null, contentValues);
        db.close(); // Cierra la base de datos para liberar recursos
        return id; // Retorna el ID del producto insertado
    }

    // Método para obtener todos los productos de la base de datos
    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase(); // Obtiene base de datos en modo lectura
        // Consulta todos los registros de la tabla y devuelve un cursor para navegar los resultados
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // Método para actualizar un producto existente
    public void updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase(); // Obtiene base de datos en modo escritura
        ContentValues contentValues = new ContentValues(); // Contenedor de valores para actualizar
        contentValues.put(COLUMN_NAME, product.getName()); // Actualiza el nombre del producto
        contentValues.put(COLUMN_PRICE, product.getPrice()); // Actualiza el precio del producto
        contentValues.put("product_date", product.getDate()); // Actualiza la fecha del producto

        // Actualiza el producto especificado por su ID
        db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{String.valueOf(product.getId())});
        db.close(); // Cierra la base de datos
    }

    // Método para eliminar un producto por su ID
    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase(); // Obtiene base de datos en modo escritura
        // Elimina el producto con el ID especificado
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close(); // Cierra la base de datos
    }
}
