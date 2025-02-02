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
import java.util.HashMap;
import java.util.Map;

public class Salados extends AppCompatActivity {

    //Creamos las variables de instancia.
    RadioButton saladitosRB;
    RadioButton flautasRB;
    RadioButton tostadasRB;
    RadioButton tapasRB;
    Map<String, Double> preciosPastel;

    Button añadirButton;
    Button volverSalado;
    Button irCanastaSalado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_salados);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Relacionamos la parte gráfica con la lógica.
        saladitosRB=findViewById(R.id.saladitosRB);
        flautasRB=findViewById(R.id.flautaRB);
        tostadasRB=findViewById(R.id.tostadaRB);
        tapasRB=findViewById(R.id.tapaRB);

        añadirButton=findViewById(R.id.añadirSaladoButton);
        volverSalado=findViewById(R.id.saladoVolverButton);
        irCanastaSalado=findViewById(R.id.saladoCanastaButton);

        // Inicializa el mapa de precios de salados.
        preciosPastel = new HashMap<>();
        preciosPastel.put("Saladitos Variados", 3.00);
        preciosPastel.put("Flauta", 4.30);
        preciosPastel.put("Tostada", 2.20);
        preciosPastel.put("Tapas variadas", 6.00);
    }

    public void añadirSalado(View view){
        File file = new File(getFilesDir(), "Recibo.txt");

        try (FileWriter fileWriter = new FileWriter(file, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){

            String tipoSalado = null;
            double precio = 0;

            // Comprobar cuál RadioButton está seleccionado.
            if (saladitosRB.isChecked()) {
                tipoSalado = "Saladitos Variados";
                precio = preciosPastel.get(tipoSalado);
            } else if (flautasRB.isChecked()) {
                tipoSalado = "Flauta";
                precio = preciosPastel.get(tipoSalado);
            } else if (tostadasRB.isChecked()) {
                tipoSalado = "Tostada";
                precio = preciosPastel.get(tipoSalado);
            } else if (tapasRB.isChecked()) {
                tipoSalado = "Tapas variadas";
                precio = preciosPastel.get(tipoSalado);
            } else {
                bufferedWriter.write("Ningún aperitivo salado seleccionado.\n");
                Intent go = new Intent(this, ConsultarProductos.class);
                startActivity(go);
                return;
            }

            //Añadimos el cafe en el fichero.
            bufferedWriter.write(tipoSalado + " añadido. Precio: $" + precio + "\n");
            Toast.makeText(this,"Producto añadido con éxito",Toast.LENGTH_SHORT).show();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saladoToCatalog(View view){
        finish();
    }

    public void saladoToConsulta(View view){
        Intent go = new Intent(this, ConsultarProductos.class);
        startActivity(go);
    }

}