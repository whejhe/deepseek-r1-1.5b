package com.dam.aplicacionandroidpersonalizada;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText nameFieldET;
    Button sendButton;

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
        nameFieldET=findViewById(R.id.nameField);
        sendButton=findViewById(R.id.sendButton);
    }

    public void goToCatalogo(View view){
        Intent intent = new Intent(this, Catalogo.class);

        //Recibe la cadena escrita en el edit text y la envía a la siguiente activity
        intent.putExtra("nombre", nameFieldET.getText().toString());
        startActivity(intent);
    }

}