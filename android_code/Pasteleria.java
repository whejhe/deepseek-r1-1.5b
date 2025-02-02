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

public class Pasteleria extends AppCompatActivity {

    //Creamos las variables de instancia.
    RadioButton zanahoriaRB;
    RadioButton satcherRB;
    RadioButton fresaRB;
    RadioButton quesoRB;
    Map<String, Double> preciosPastel;

    Button añadirButton;
    Button volverPastel;
    Button irCanastaPastel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_postres);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Relacionamos la parte gráfica con la lógica.
        zanahoriaRB=findViewById(R.id.zanahoriaRB);
        satcherRB=findViewById(R.id.satcherRB);
        fresaRB=findViewById(R.id.fresaRB);
        quesoRB=findViewById(R.id.quesoRB);

        añadirButton=findViewById(R.id.añadirPastelButton);
        volverPastel=findViewById(R.id.pastelVolverButton);
        irCanastaPastel=findViewById(R.id.pastelCanastaButton);

        // Inicializa el mapa de precios de pastel
        preciosPastel = new HashMap<>();
        preciosPastel.put("Pastel de Zanahoria", 4.50);
        preciosPastel.put("Pastel Satcher", 5.00);
        preciosPastel.put("Pastel de Fresa", 4.00);
        preciosPastel.put("Tarta de Queso", 4.50);
    }

    public void añadirPostre(View view){

        File file = new File(getFilesDir(), "Recibo.txt");

        try (FileWriter fileWriter = new FileWriter(file, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){

            String tipoPastel = null;
            double precio = 0;

            // Comprobar cuál RadioButton está seleccionado.
            if (zanahoriaRB.isChecked()) {
                tipoPastel = "Pastel de Zanahoria";
                precio = preciosPastel.get(tipoPastel);
            } else if (satcherRB.isChecked()) {
                tipoPastel = "Pastel Satcher";
                precio = preciosPastel.get(tipoPastel);
            } else if (fresaRB.isChecked()) {
                tipoPastel = "Pastel de Fresa";
                precio = preciosPastel.get(tipoPastel);
            } else if (quesoRB.isChecked()) {
                tipoPastel = "Tarta de Queso";
                precio = preciosPastel.get(tipoPastel);
            } else {
                bufferedWriter.write("Ningún dulce seleccionado.\n");
                Intent go = new Intent(this, ConsultarProductos.class);
                startActivity(go);
                return;
            }

            //Añadimos el cafe en el fichero.
            bufferedWriter.write(tipoPastel + " añadido. Precio: $" + precio + "\n");
            Toast.makeText(this,"Producto añadido con éxito",Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void pastelToCatalog(View view){
        finish();
    }

    public void pastelToConsulta(View view){
        Intent go = new Intent(this, ConsultarProductos.class);
        startActivity(go);
    }
}