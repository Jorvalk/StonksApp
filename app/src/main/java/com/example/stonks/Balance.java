package com.example.stonks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.text.NumberFormat;
import java.util.Locale;

public class Balance extends AppCompatActivity {

    private EditText editTextSueldo;
    private Button buttonIngresar, buttonBack;
    private TextView textViewNecesidades, textViewDeseos, textViewAhorros;
    private ProgressBar progressBarNecesidades, progressBarDeseos, progressBarAhorros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_balance);

        // Referencias a los views
        editTextSueldo = findViewById(R.id.editTextSueldo);
        buttonIngresar = findViewById(R.id.buttonIngresar);
        buttonBack = findViewById(R.id.buttonBack);
        textViewNecesidades = findViewById(R.id.textViewNecesidades);
        textViewDeseos = findViewById(R.id.textViewDeseos);
        textViewAhorros = findViewById(R.id.textViewAhorros);
        progressBarNecesidades = findViewById(R.id.progressBarNecesidades);
        progressBarDeseos = findViewById(R.id.progressBarDeseos);
        progressBarAhorros = findViewById(R.id.progressBarAhorros);

        // Botón de ingresar sueldo
        buttonIngresar.setOnClickListener(v -> {
            String sueldoStr = editTextSueldo.getText().toString();
            if (sueldoStr.isEmpty()) {
                Toast.makeText(Balance.this, "Ingresa un sueldo válido", Toast.LENGTH_SHORT).show();
            } else {
                double sueldo = Double.parseDouble(sueldoStr);
                mostrarOpcionesDistribucion(sueldo);
            }
        });

        // Botón de volver
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(Balance.this, Main.class);
            startActivity(intent);
            finish();
        });

        // Ajuste de padding por insets del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Mostrar opciones de distribución
    private void mostrarOpcionesDistribucion(double sueldo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona una opción");
        builder.setItems(new CharSequence[]{"Aplicar 50-30-20", "Personalizar porcentajes"}, (dialog, which) -> {
            if (which == 0) {
                aplicarRegla503020(sueldo);
            } else {
                personalizarPorcentajes(sueldo);
            }
        });
        builder.show();
    }

    // Aplicar regla 50-30-20
    private void aplicarRegla503020(double sueldo) {
        double necesidades = sueldo * 0.50;
        double deseos = sueldo * 0.30;
        double ahorro = sueldo * 0.20;

        actualizarUI(necesidades, deseos, ahorro);
    }

    // Personalizar porcentajes
    private void personalizarPorcentajes(double sueldo) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_porcentajes, null);
        EditText editTextNecesidades = dialogView.findViewById(R.id.editTextNecesidades);
        EditText editTextDeseos = dialogView.findViewById(R.id.editTextDeseos);
        EditText editTextAhorro = dialogView.findViewById(R.id.editTextAhorro);
        Button buttonAplicar = dialogView.findViewById(R.id.buttonAplicar);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Personaliza tus porcentajes");
        builder.setView(dialogView);
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();

        buttonAplicar.setOnClickListener(v -> {
            String necesidadesStr = editTextNecesidades.getText().toString();
            String deseosStr = editTextDeseos.getText().toString();
            String ahorroStr = editTextAhorro.getText().toString();

            if (necesidadesStr.isEmpty() || deseosStr.isEmpty() || ahorroStr.isEmpty()) {
                Toast.makeText(Balance.this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int necesidades = Integer.parseInt(necesidadesStr);
            int deseos = Integer.parseInt(deseosStr);
            int ahorro = Integer.parseInt(ahorroStr);

            if (necesidades + deseos + ahorro != 100) {
                Toast.makeText(Balance.this, "Los porcentajes deben sumar 100", Toast.LENGTH_SHORT).show();
            } else {
                double montoNecesidades = sueldo * (necesidades / 100.0);
                double montoDeseos = sueldo * (deseos / 100.0);
                double montoAhorro = sueldo * (ahorro / 100.0);

                actualizarUI(montoNecesidades, montoDeseos, montoAhorro);
                dialog.dismiss();
            }
        });
    }

    // Actualizar TextViews y ProgressBars
    private void actualizarUI(double necesidades, double deseos, double ahorro) {
        // Formatear en pesos chilenos
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "CL"));

        // Actualizar TextViews con formato en pesos chilenos
        textViewNecesidades.setText("Necesidades: " + formatter.format(necesidades));
        textViewDeseos.setText("Deseos: " + formatter.format(deseos));
        textViewAhorros.setText("Ahorros: " + formatter.format(ahorro));

        // Calcular los porcentajes para las barras de progreso
        int progresoNecesidades = (int) (necesidades * 100 / (necesidades + deseos + ahorro));
        int progresoDeseos = (int) (deseos * 100 / (necesidades + deseos + ahorro));
        int progresoAhorro = (int) (ahorro * 100 / (necesidades + deseos + ahorro));

        // Actualizar las ProgressBars
        progressBarNecesidades.setProgress(progresoNecesidades);
        progressBarDeseos.setProgress(progresoDeseos);
        progressBarAhorros.setProgress(progresoAhorro);
    }
}
