package com.example.histoireapp;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pays);

        db = new DatabaseHelper(this);
        etNom = findViewById(R.id.etNom);
        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> savePays());
    }

    private void savePays() {
        String nom = etNom.getText().toString().trim();

        if (nom.isEmpty()) {
            Toast.makeText(this, "Veuillez saisir le nom du pays", Toast.LENGTH_SHORT).show();
            return;
        }

        Pays pays = new Pays(nom);
        long result = db.insertPays(pays);

        if (result != -1) {
            Toast.makeText(this, "Pays enregistré avec succès", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();
        }
    }
}