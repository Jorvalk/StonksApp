package com.example.stonks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Enroll extends AppCompatActivity {

    private Button buttonBack;
    private Button buttonRegister;
    private EditText editTextName, editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enroll);

        // Configurar padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonBack = findViewById(R.id.buttonBack);
        buttonRegister = findViewById(R.id.buttonRegister);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        // Regresar a MainActivity
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(Enroll.this, Main.class);
            startActivity(intent);
            finish();
        });

        // Lógica para registrar usuario
        buttonRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        // Validar campos
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Aquí agregar la lógica para registrar al usuario (por ejemplo, almacenar en base de datos)
        // ...

        Toast.makeText(this, "Registro exitoso!", Toast.LENGTH_SHORT).show();
    }
}
