package com.example.pruebafirebase2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button addBookButton, viewBooksButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBookButton = findViewById(R.id.agregarLibroBtn);
        viewBooksButton = findViewById(R.id.listarBtn);
        logoutButton = findViewById(R.id.logoutBtn);

        addBookButton.setOnClickListener(v -> startActivity(new Intent(this, AgregarLibro.class)));
        viewBooksButton.setOnClickListener(v -> startActivity(new Intent(this, Lista.class)));
        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, Login.class));
            finish();
        });
    }
}
