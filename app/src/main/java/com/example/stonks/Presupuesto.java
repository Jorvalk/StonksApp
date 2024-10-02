package com.example.stonks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Presupuesto extends AppCompatActivity {

    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_presupuesto);

        buttonBack = findViewById(R.id.buttonBack); // Obtener referencia al botón de retroceder

        // Función para regresar a la actividad MainActivity
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(Presupuesto.this, Main.class);
            startActivity(intent);
            finish(); // Cierra la actividad actual para evitar que el usuario vuelva atrás con el botón de retroceso
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
