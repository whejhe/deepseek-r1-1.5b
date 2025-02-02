package com.dam.aplicacionandroidpersonalizada;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Catalogo extends AppCompatActivity {
    //Declaración de variables de instancia.
    TextView welcomeTV;
    ImageButton cafeIM;
    ImageButton pastelIM;
    ImageButton saladosIM;
    Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_catalogo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Relacionamos cada elemento xml con el código.
        welcomeTV = findViewById(R.id.welcomeTV);

        //Botones
        cafeIM = findViewById(R.id.cafeImButton);
        pastelIM = findViewById(R.id.pastelImButton);
        saladosIM = findViewById(R.id.saladoImButton);
        goBack = findViewById(R.id.backButton);

        //Recibimos el String enviado desde la anterior Activity.
        String nombre =  getIntent().getStringExtra("nombre");

        //Nos aseguramos de tratar con posible dato nulo y lo mostramos.
        String nombreUsuario = (nombre != null? nombre : "invitado");
        String mensaje = getString(R.string.welcomeMessage, nombreUsuario);

        welcomeTV.setText(mensaje);
    }

    public void goToCafeteria(View view){
        Intent next = new Intent(this, Cafeteria.class);
        startActivity(next);
    }

    public void goToPasteleria(View view){
        Intent next = new Intent(this, Pasteleria.class);
        startActivity(next);
    }

    public void goToSalados(View view){
        Intent next = new Intent(this, Salados.class);
        startActivity(next);
    }

    public void goBackMain(View view){
        Intent back = new Intent(this, MainActivity.class);
        startActivity(back);
        finish();
    }
}