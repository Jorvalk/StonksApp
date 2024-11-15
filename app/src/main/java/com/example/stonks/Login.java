package com.example.stonks;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private Button buttonBack;
    private Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private CheckBox checkBoxShowPassword;
    private FirebaseAuth auth;
    private TextView textViewSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Configurar padding para evitar superposición con las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonBack = findViewById(R.id.buttonBack);
        buttonLogin = findViewById(R.id.buttonLogin);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        checkBoxShowPassword = findViewById(R.id.checkBoxShowPassword);
        textViewSignUp = findViewById(R.id.textViewSignUp);

        auth = FirebaseAuth.getInstance();  // Inicializa FirebaseAuth

        // Función para regresar a la actividad MainActivity
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Main.class);
            startActivity(intent);
            finish();
        });

        // Configura el evento para el TextView de registro
        textViewSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Enroll.class);
            startActivity(intent);
        });

        // Lógica para mostrar u ocultar la contraseña
        checkBoxShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            editTextPassword.setSelection(editTextPassword.getText().length());
        });

        // Lógica para el botón de inicio de sesión
        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // Validar campos
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Por favor, ingresa tus credenciales", Toast.LENGTH_SHORT).show();
            } else {
                // Autenticar al usuario con Firebase
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Inicio de sesión exitoso
                                Toast.makeText(Login.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, Main.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Manejo de errores
                                Toast.makeText(Login.this, "Error al iniciar sesión: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}
