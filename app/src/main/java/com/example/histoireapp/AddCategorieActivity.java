package com.example.histoireapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.histoireapp.R;
import com.example.histoireapp.DatabaseHelper;
import com.example.histoireapp.Categorie;

public class AddCategorieActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private EditText etNom;
    private int categorieId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categorie);

        db = new DatabaseHelper(this);
        etNom = findViewById(R.id.etNom);
        Button btnSave = findViewById(R.id.btnSave);

        // Check if we're editing an existing categorie
        categorieId = getIntent().getIntExtra("CATEGORIE_ID", -1);
        if (categorieId != -1) {
            // Load categorie data
            Categorie categorie = db.getCategorie(categorieId);
            etNom.setText(categorie.getNom());

            // Change button text
            btnSave.setText("Modifier");
        }

        btnSave.setOnClickListener(v -> saveCategorie());
    }

    private void saveCategorie() {
        String nom = etNom.getText().toString().trim();

        if (nom.isEmpty()) {
            Toast.makeText(this, "Veuillez saisir le nom de la catégorie", Toast.LENGTH_SHORT).show();
            return;
        }

        Categorie categorie = new Categorie(nom);
        boolean success = false;

        if (categorieId != -1) {
            categorie.setId(categorieId);
            int updateResult = db.updateCategorie(categorie);
            success = (updateResult > 0);
            if (success) {
                Toast.makeText(this, "Catégorie modifiée avec succès", Toast.LENGTH_SHORT).show();
            }
        } else {
            long insertResult = db.insertCategorie(categorie);
            success = (insertResult != -1);
            if (success) {
                Toast.makeText(this, "Catégorie créée avec succès", Toast.LENGTH_SHORT).show();
            }
        }

        if (success) {
            finish();
        } else {
            Toast.makeText(this, "Erreur lors de l'opération", Toast.LENGTH_SHORT).show();
        }
    }
}