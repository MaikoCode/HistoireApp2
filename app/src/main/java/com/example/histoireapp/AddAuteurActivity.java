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
import com.example.histoireapp.Auteur;
import com.example.histoireapp.Pays;
import java.util.List;

public class AddAuteurActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private EditText etNom, etPrenom;
    private Spinner spinnerPays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_auteur);

        db = new DatabaseHelper(this);

        etNom = findViewById(R.id.etNom);
        etPrenom = findViewById(R.id.etPrenom);
        spinnerPays = findViewById(R.id.spinnerPays);
        Button btnSave = findViewById(R.id.btnSave);

        loadPaysSpinner();

        btnSave.setOnClickListener(v -> saveAuteur());
    }

    private void loadPaysSpinner() {
        List<Pays> paysList = db.getAllPays();
        ArrayAdapter<Pays> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, paysList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPays.setAdapter(adapter);
    }

    private void saveAuteur() {
        String nom = etNom.getText().toString().trim();
        String prenom = etPrenom.getText().toString().trim();

        if (nom.isEmpty() || prenom.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        Pays selectedPays = (Pays) spinnerPays.getSelectedItem();

        Auteur auteur = new Auteur(nom, prenom, selectedPays.getId());

        long result = db.insertAuteur(auteur);
        if (result != -1) {
            Toast.makeText(this, "Auteur enregistré avec succès", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();
        }
    }
}