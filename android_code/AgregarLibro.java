package com.example.pruebafirebase2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AgregarLibro extends AppCompatActivity {
    private EditText titleEditText, authorEditText, yearEditText;
    private Button saveBookButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_libro);

        titleEditText = findViewById(R.id.titleET);
        authorEditText = findViewById(R.id.autorET);
        yearEditText = findViewById(R.id.añoET);
        saveBookButton = findViewById(R.id.guardarBtn);

        db = FirebaseFirestore.getInstance();

        saveBookButton.setOnClickListener(v -> saveBook());
    }

    private void saveBook() {
        String title = titleEditText.getText().toString();
        String author = authorEditText.getText().toString();
        String year = yearEditText.getText().toString();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(year)) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> book = new HashMap<>();
        book.put("title", title);
        book.put("author", author);
        book.put("year", year);
        book.put("userId", userId);

        db.collection("books").add(book).addOnSuccessListener(documentReference -> {
            Toast.makeText(this, "Libro agregado exitosamente", Toast.LENGTH_SHORT).show();
            finish();
        }).addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
