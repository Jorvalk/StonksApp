package com.example.stonks;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {

    private Button buttonBack; // Botón de retroceder
    private Button buttonLogin; // Botón de inicio de sesión
    private EditText editTextEmail; // EditText para correo electrónico
    private EditText editTextPassword; // EditText para contraseña
    private CheckBox checkBoxShowPassword; // CheckBox para mostrar/ocultar contraseña

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

        buttonBack = findViewById(R.id.buttonBack); // Inicializamos el botón de retroceder
        buttonLogin = findViewById(R.id.buttonLogin); // Inicializamos el botón de inicio de sesión
        editTextEmail = findViewById(R.id.editTextEmail); // Inicializamos EditText para el correo
        editTextPassword = findViewById(R.id.editTextPassword); // Inicializamos EditText para la contraseña
        checkBoxShowPassword = findViewById(R.id.checkBoxShowPassword); // Inicializamos CheckBox

        // Función para regresar a la actividad MainActivity
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Main.class);
            startActivity(intent);
            finish(); // Cierra la actividad actual
        });

        // Lógica para mostrar u ocultar la contraseña
        checkBoxShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Mostrar la contraseña
                editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                // Ocultar la contraseña
                editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            // Mover el cursor al final del texto
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
                // Aquí puedes agregar tu lógica para iniciar sesión
                // Por ejemplo, verificar las credenciales

                // Si el inicio de sesión es exitoso, redirigir a otra actividad
                Intent intent = new Intent(Login.this, Main.class); // Cambia 'Main.class' a la actividad que desees
                startActivity(intent);
                finish(); // Cierra la actividad actual
            }
        });
    }
}
