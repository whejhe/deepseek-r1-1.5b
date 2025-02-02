package com.dam.aplicacionandroidpersonalizada;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Cafeteria extends AppCompatActivity {

    //Creamos los objetos
    RadioButton expressoRB;
    RadioButton americanoRB;
    RadioButton conLecheRB;
    RadioButton bombonRB;

    Button añadirCafeB;
    Button volverCafeB;
    Button irConsultaCafeB;

    Map<String, Double> preciosCafe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cafeteria);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Relacionamos parte grafica y logica

        //RadioButtons.
        expressoRB = findViewById(R.id.cafeExpresoRB);
        americanoRB = findViewById(R.id.cafeAmericanoRB);
        conLecheRB = findViewById(R.id.cafeLecheRB);
        bombonRB = findViewById(R.id.cafeBombonRB);

        //Buttons.
        añadirCafeB = findViewById(R.id.añadirCafeButton);
        volverCafeB = findViewById(R.id.volverCafeButton);
        irConsultaCafeB = findViewById(R.id.irReciboCafeButton);

        // Inicializa el mapa de precios de café.
        preciosCafe = new HashMap<>();
        preciosCafe.put("Café Expreso", 1.50);
        preciosCafe.put("Café Americano", 2.00);
        preciosCafe.put("Café con Leche", 2.50);
        preciosCafe.put("Café Bombón", 3.00);
    }

    public void añadirCafe(View view){

        File file = new File(getFilesDir(), "Recibo.txt");
        try (FileWriter fileWriter = new FileWriter(file, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){

            String tipoCafe = null;
            double precio = 0;

            // Comprobar cuál RadioButton está seleccionado.
            if (expressoRB.isChecked()) {
                tipoCafe = "Café Expresso";
                precio = preciosCafe.get(tipoCafe);
            } else if (americanoRB.isChecked()) {
                tipoCafe = "Café Americano";
                precio = preciosCafe.get(tipoCafe);
            } else if (conLecheRB.isChecked()) {
                tipoCafe = "Café con Leche";
                precio = preciosCafe.get(tipoCafe);
            } else if (bombonRB.isChecked()) {
                tipoCafe = "Café Bombón";
                precio = preciosCafe.get(tipoCafe);
            } else {
                bufferedWriter.write("Ningún café seleccionado.\n");
                Intent go = new Intent(this, ConsultarProductos.class);
                startActivity(go);
                return;
            }

            //Añadimos el cafe en el fichero.
            bufferedWriter.write(tipoCafe + " añadido. Precio: $" + precio + "\n");
            Toast.makeText(this,"Producto añadido con éxito",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goBackCatalog(View view){
        finish();
    }

    public void gotoConsulta(View view){
        Intent go = new Intent(this, ConsultarProductos.class);
        startActivity(go);
    }
}