package com.example.stonks;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Balance Button
        Button buttonBalance = findViewById(R.id.buttonBalance);
        buttonBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this, Balance.class);
                startActivity(intent);
            }
        });

        // Resumen Button
        Button buttonResumen = findViewById(R.id.buttonResumen);
        buttonResumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this, Resumen.class);
                startActivity(intent);
            }
        });

        // Presupuesto Button
        Button buttonPresupuesto = findViewById(R.id.buttonPresupuesto);
        buttonPresupuesto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this, Presupuesto.class);
                startActivity(intent);
            }
        });

        // Imagen Button
        ImageView imageUser = findViewById(R.id.imageUser);
        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear el intento para abrir la actividad Login
                Intent intent = new Intent(Main.this, Login.class);
                startActivity(intent);  // Iniciar la actividad Login
            }
        });
    }
}
