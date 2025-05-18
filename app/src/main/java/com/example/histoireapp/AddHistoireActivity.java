package com.example.histoireapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.histoireapp.R;
import com.example.histoireapp.DatabaseHelper;
import com.example.histoireapp.Auteur;
import com.example.histoireapp.Categorie;
import com.example.histoireapp.Histoire;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddHistoireActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;

    private DatabaseHelper db;
    private EditText etTitre, etContenu;
    private Spinner spinnerAuteur, spinnerCategorie;
    private ImageView imageView;
    private String currentPhotoPath;
    private int histoireId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_histoire);

        db = new DatabaseHelper(this);

        etTitre = findViewById(R.id.etTitre);
        etContenu = findViewById(R.id.etContenu);
        spinnerAuteur = findViewById(R.id.spinnerAuteur);
        spinnerCategorie = findViewById(R.id.spinnerCategorie);
        imageView = findViewById(R.id.imageView);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnTakePhoto = findViewById(R.id.btnTakePhoto);

        loadSpinners();

        histoireId = getIntent().getIntExtra("HISTOIRE_ID", -1);
        if (histoireId != -1) {
            Histoire histoire = db.getHistoire(histoireId);
            etTitre.setText(histoire.getTitre());
            etContenu.setText(histoire.getContenu());
            setSpinnerSelection(spinnerAuteur, histoire.getAuteurId());
            setSpinnerSelection(spinnerCategorie, histoire.getCategorieId());
            btnSave.setText("Modifier");
        }

        btnSave.setOnClickListener(v -> saveHistoire());
        btnTakePhoto.setOnClickListener(v -> {
            if (checkPermissions()) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void loadSpinners() {
        List<Auteur> auteurs = db.getAllAuteurs();
        ArrayAdapter<Auteur> auteurAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, auteurs);
        auteurAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAuteur.setAdapter(auteurAdapter);

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

    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Erreur lors de la création du fichier",
                        Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.histoireapp.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            setPic();
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
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
        boolean success = false;

        if (histoireId != -1) {
            histoire.setId(histoireId);
            int updateResult = db.updateHistoire(histoire);
            success = (updateResult > 0);
            if (success) {
                Toast.makeText(this, "Histoire modifiée avec succès", Toast.LENGTH_SHORT).show();
            }
        } else {
            long insertResult = db.insertHistoire(histoire);
            success = (insertResult != -1);
            if (success) {
                Toast.makeText(this, "Histoire créée avec succès", Toast.LENGTH_SHORT).show();
            }
        }

        if (success) {
            finish();
        } else {
            Toast.makeText(this, "Erreur lors de l'opération", Toast.LENGTH_SHORT).show();
        }
    }
}