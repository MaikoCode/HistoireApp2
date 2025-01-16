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
    private int auteurId = -1;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_auteur);
//
//        db = new DatabaseHelper(this);
//
//        etNom = findViewById(R.id.etNom);
//        etPrenom = findViewById(R.id.etPrenom);
//        spinnerPays = findViewById(R.id.spinnerPays);
//        Button btnSave = findViewById(R.id.btnSave);
//
//
//        loadPaysSpinner();
//
//        btnSave.setOnClickListener(v -> saveAuteur());
//    }
private void setSpinnerSelection(int paysId) {
    for (int i = 0; i < spinnerPays.getAdapter().getCount(); i++) {
        Pays pays = (Pays) spinnerPays.getAdapter().getItem(i);
        if (pays.getId() == paysId) {
            spinnerPays.setSelection(i);
            break;
        }
    }
}
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

    // Cette partie est importante pour récupérer l'ID
    auteurId = getIntent().getIntExtra("AUTEUR_ID", -1);
    if (auteurId != -1) {
        // Load auteur data
        Auteur auteur = db.getAuteur(auteurId);
        etNom.setText(auteur.getNom());
        etPrenom.setText(auteur.getPrenom());

        // Set spinner selection
        setSpinnerSelection(auteur.getPaysId());

        // Change button text
        btnSave.setText("Modifier");
    }

    btnSave.setOnClickListener(v -> saveAuteur());
}
    private void loadPaysSpinner() {
        List<Pays> paysList = db.getAllPays();
        ArrayAdapter<Pays> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, paysList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPays.setAdapter(adapter);
    }

//    private void saveAuteur() {
//        String nom = etNom.getText().toString().trim();
//        String prenom = etPrenom.getText().toString().trim();
//
//        if (nom.isEmpty() || prenom.isEmpty()) {
//            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Pays selectedPays = (Pays) spinnerPays.getSelectedItem();
//
//        Auteur auteur = new Auteur(nom, prenom, selectedPays.getId());
//
//        long result = db.insertAuteur(auteur);
//        if (result != -1) {
//            Toast.makeText(this, "Auteur enregistré avec succès", Toast.LENGTH_SHORT).show();
//            finish();
//        } else {
//            Toast.makeText(this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void saveAuteur() {
        String nom = etNom.getText().toString().trim();
        String prenom = etPrenom.getText().toString().trim();

        if (nom.isEmpty() || prenom.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        Pays selectedPays = (Pays) spinnerPays.getSelectedItem();
        Auteur auteur = new Auteur(nom, prenom, selectedPays.getId());

        boolean success = false;
        if (auteurId != -1) {
            auteur.setId(auteurId);  // Important : définir l'ID pour la mise à jour
            int updateResult = db.updateAuteur(auteur);
            success = (updateResult > 0);
            if (success) {
                Toast.makeText(this, "Auteur modifié avec succès", Toast.LENGTH_SHORT).show();
            }
        } else {
            long insertResult = db.insertAuteur(auteur);
            success = (insertResult != -1);
            if (success) {
                Toast.makeText(this, "Auteur créé avec succès", Toast.LENGTH_SHORT).show();
            }
        }

        if (success) {
            finish();
        } else {
            Toast.makeText(this, "Erreur lors de l'opération", Toast.LENGTH_SHORT).show();
        }
    }
}