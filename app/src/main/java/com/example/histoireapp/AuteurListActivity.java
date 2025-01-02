package com.example.histoireapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.histoireapp.R;
import com.example.histoireapp.AuteurAdapter;
import com.example.histoireapp.DatabaseHelper;
import com.example.histoireapp.Auteur;
import java.util.List;

public class AuteurListActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private RecyclerView recyclerView;
    private AuteurAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auteur_list);

        db = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadAuteurs();

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddAuteurActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAuteurs();
    }

    private void loadAuteurs() {
        List<Auteur> auteurs = db.getAllAuteurs();
        adapter = new AuteurAdapter(this, auteurs);
        recyclerView.setAdapter(adapter);
    }
}