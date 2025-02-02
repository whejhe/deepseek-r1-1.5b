package com.dam.aplicacionandroidpersonalizada;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConsultarProductos extends AppCompatActivity {

    TextView consultaTitleTV;
    EditText consultaListET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consultar_productos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        consultaListET=findViewById(R.id.consultaListET);
        consultaTitleTV=findViewById(R.id.consultaTitleTV);


        String mensaje = getString(R.string.consultaTitle);

        consultaTitleTV.setText(mensaje);
        File file = new File(getFilesDir(), "Recibo.txt");

        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)){

            String linea;
            StringBuilder stringBuilder = new StringBuilder();

            // Lee línea por línea y agrega al StringBuilder
            while ((linea = bufferedReader.readLine()) != null) {
                stringBuilder.append(linea).append("\n"); // Añadir línea al contenido
            }

            // Mostrar el contenido en el TextView
            consultaListET.setText(stringBuilder.toString());

        } catch (FileNotFoundException e) {
            consultaListET.setText("Canasta vacía.");
        } catch (IOException e) {
            consultaListET.setText("Error al leer el archivo.");
        }
    }

    public void goBackConsulta(View view){
        Intent back = new Intent(this, Catalogo.class);
        startActivity(back);
    }

    public void deleteList(View view) {
        File file = new File(getFilesDir(), "Recibo.txt");

        if (file.exists()){
            file.delete();
            Toast.makeText(this,"Lista eliminada.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"No hay lista que eliminar.",Toast.LENGTH_SHORT).show();
        }
    }
}