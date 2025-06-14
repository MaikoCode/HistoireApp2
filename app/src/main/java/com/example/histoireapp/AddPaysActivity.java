package com.example.histoireapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.histoireapp.R;
import com.example.histoireapp.DatabaseHelper;
import com.example.histoireapp.Pays;

public class AddPaysActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private EditText etNom;
    private int paysId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pays);

        db = new DatabaseHelper(this);
        etNom = findViewById(R.id.etNom);
        Button btnSave = findViewById(R.id.btnSave);

        // Check if we're editing an existing pays
        paysId = getIntent().getIntExtra("PAYS_ID", -1);
        if (paysId != -1) {
            // Load pays data
            Pays pays = db.getPays(paysId);
            etNom.setText(pays.getNom());

            // Change button text
            btnSave.setText("Modifier");
        }

        btnSave.setOnClickListener(v -> savePays());
    }

    private void savePays() {
        String nom = etNom.getText().toString().trim();

        if (nom.isEmpty()) {
            Toast.makeText(this, "Veuillez saisir le nom du pays", Toast.LENGTH_SHORT).show();
            return;
        }

        Pays pays = new Pays(nom);
        long result;

        if (paysId != -1) {
            pays.setId(paysId);
            result = db.updatePays(pays);
            Toast.makeText(this, "Pays modifié avec succès", Toast.LENGTH_SHORT).show();
        } else {
            result = db.insertPays(pays);
            Toast.makeText(this, "Pays créé avec succès", Toast.LENGTH_SHORT).show();
        }

        if (result != -1) {
            finish();
        } else {
            Toast.makeText(this, "Erreur lors de l'opération", Toast.LENGTH_SHORT).show();
        }
    }
}