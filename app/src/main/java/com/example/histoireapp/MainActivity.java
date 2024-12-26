package com.example.histoireapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cardHistoire, cardAuteur, cardPays, cardCategorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        cardHistoire = findViewById(R.id.cardHistoire);
        cardAuteur = findViewById(R.id.cardAuteur);
        cardPays = findViewById(R.id.cardPays);
        cardCategorie = findViewById(R.id.cardCategorie);

        // Set click listeners
        cardHistoire.setOnClickListener(this);
        cardAuteur.setOnClickListener(this);
        cardPays.setOnClickListener(this);
        cardCategorie.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        if (v.getId() == R.id.cardHistoire) {
            // Will be implemented: intent = new Intent(this, HistoireListActivity.class);
            // For now, we'll just show a message
            Toast.makeText(this, "Histoire section coming soon", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.cardAuteur) {
            // Will be implemented: intent = new Intent(this, AuteurListActivity.class);
            Toast.makeText(this, "Auteur section coming soon", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.cardPays) {
            // Will be implemented: intent = new Intent(this, PaysListActivity.class);
            Toast.makeText(this, "Pays section coming soon", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.cardCategorie) {
            // Will be implemented: intent = new Intent(this, CategorieListActivity.class);
            Toast.makeText(this, "Categorie section coming soon", Toast.LENGTH_SHORT).show();
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}