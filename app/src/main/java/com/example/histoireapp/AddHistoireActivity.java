package com.example.histoireapp;

import android.content.Intent;
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
    private int histoireId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_histoire);

        db = new DatabaseHelper(this);

        // Initialize views
        etTitre = findViewById(R.id.etTitre);
        etContenu = findViewById(R.id.etContenu);
        spinnerAuteur = findViewById(R.id.spinnerAuteur);
        spinnerCategorie = findViewById(R.id.spinnerCategorie);
        Button btnSave = findViewById(R.id.btnSave);

        // Load spinners
        loadSpinners();

        // Check if we're editing an existing histoire
        histoireId = getIntent().getIntExtra("HISTOIRE_ID", -1);
        if (histoireId != -1) {
            // Load histoire data
            Histoire histoire = db.getHistoire(histoireId);
            etTitre.setText(histoire.getTitre());
            etContenu.setText(histoire.getContenu());

            // Set spinners selection
            setSpinnerSelection(spinnerAuteur, histoire.getAuteurId());
            setSpinnerSelection(spinnerCategorie, histoire.getCategorieId());

            // Change button text
            btnSave.setText("Modifier");
        }

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

    private void setSpinnerSelection(Spinner spinner, int id) {
        for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
            Object item = spinner.getAdapter().getItem(i);
            if (item instanceof Auteur && ((Auteur) item).getId() == id) {
                spinner.setSelection(i);
                break;
            } else if (item instanceof Categorie && ((Categorie) item).getId() == id) {
                spinner.setSelection(i);
                break;
            }
        }
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

        Histoire histoire = new Histoire(titre, contenu,
                selectedAuteur.getId(),
                selectedCategorie.getId());

        long result;
        if (histoireId != -1) {
            histoire.setId(histoireId);
            result = db.updateHistoire(histoire);
            Toast.makeText(this, "Histoire modifiée avec succès", Toast.LENGTH_SHORT).show();
        } else {
            result = db.insertHistoire(histoire);
            Toast.makeText(this, "Histoire créée avec succès", Toast.LENGTH_SHORT).show();
        }

        if (result != -1) {
            finish();
        } else {
            Toast.makeText(this, "Erreur lors de l'opération", Toast.LENGTH_SHORT).show();
        }
    }
}