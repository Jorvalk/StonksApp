package com.example.stonks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.stonks.Product;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Resumen.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "product_name";
    private static final String COLUMN_PRICE = "product_price";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE products (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "product_name TEXT," +
                "product_price REAL," +
                "product_date TEXT" +
                ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Método para insertar un producto
    public long insertProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, product.getName());
        contentValues.put(COLUMN_PRICE, product.getPrice());
        contentValues.put("product_date", product.getDate()); // Agrega el campo de fecha

        // Inserta el producto en la tabla y devuelve el ID del nuevo producto
        long id = db.insert(TABLE_NAME, null, contentValues);
        db.close(); // Cierra la base de datos
        return id; // Devuelve el ID del producto insertado
    }

    // Método para obtener todos los productos
    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // Método para actualizar un producto
    public void updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, product.getName());
        contentValues.put(COLUMN_PRICE, product.getPrice());
        contentValues.put("product_date", product.getDate()); // Asegúrate de incluir la fecha

        // Actualiza el producto por su ID
        db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{String.valueOf(product.getId())});
        db.close();
    }

    // Método para eliminar un producto
    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

}

