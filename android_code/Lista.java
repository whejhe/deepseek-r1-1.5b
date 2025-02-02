package com.example.pruebafirebase2;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Lista extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> bookTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        listView = findViewById(R.id.listaLV);
        bookTitles = new ArrayList<>();

        // Configuramos el adaptador simple para el ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookTitles);
        listView.setAdapter(adapter);

        loadBooks();
    }

    private void loadBooks() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance()
                .collection("books")
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        bookTitles.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String title = document.getString("title");
                            String author = document.getString("author");
                            String year = document.getString("year");

                            // Agregamos una línea de texto para cada libro
                            bookTitles.add(title + " - " + author + " (" + year + ")");
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Error al cargar los libros", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void salir(View view){
        finish();
    }
}


