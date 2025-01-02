package com.example.histoireapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.histoireapp.R;
import com.example.histoireapp.DatabaseHelper;
import com.example.histoireapp.Histoire;
import com.example.histoireapp.Auteur;
import com.example.histoireapp.Categorie;
import java.util.List;

public class AddHistoireActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private EditText etTitre, etContenu;
    private Spinner spinnerAuteur, spinnerCategorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_histoire);

        db = new DatabaseHelper(this);

        etTitre = findViewById(R.id.etTitre);
        etContenu = findViewById(R.id.etContenu);
        spinnerAuteur = findViewById(R.id.spinnerAuteur);
        spinnerCategorie = findViewById(R.id.spinnerCategorie);
        Button btnSave = findViewById(R.id.btnSave);

        // Load spinners
        loadSpinners();

        btnSave.setOnClickListener(v -> saveHistoire());
    }

    private void loadSpinners() {
        // Load authors
        List<Auteur> auteurs = db.getAllAuteurs();
        ArrayAdapter<Auteur> auteurAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, auteurs);
        auteurAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAuteur.setAdapter(auteurAdapter);

        // Load categories
        List<Categorie> categories = db.getAllCategories();
        ArrayAdapter<Categorie> categorieAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        categorieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorie.setAdapter(categorieAdapter);
    }

    private void saveHistoire() {
        String titre = etTitre.getText().toString().trim();
        String contenu = etContenu.getText().toString().trim();

        if (titre.isEmpty() || contenu.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        Auteur selectedAuteur = (Auteur) spinnerAuteur.getSelectedItem();
        Categorie selectedCategorie = (Categorie) spinnerCategorie.getSelectedItem();

        Histoire histoire = new Histoire(titre, contenu, selectedAuteur.getId(), selectedCategorie.getId());

        long result = db.insertHistoire(histoire);
        if (result != -1) {
            Toast.makeText(this, "Histoire enregistrée avec succès", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();
        }
    }
}