package com.example.histoireapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.histoireapp.R;
import com.example.histoireapp.CategorieAdapter;
import com.example.histoireapp.DatabaseHelper;
import com.example.histoireapp.Categorie;
import java.util.List;

public class CategorieListActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private RecyclerView recyclerView;
    private CategorieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie_list);

        db = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadCategories();

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddCategorieActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCategories();
    }

    private void loadCategories() {
        List<Categorie> categories = db.getAllCategories();
        adapter = new CategorieAdapter(this, categories);
        recyclerView.setAdapter(adapter);
    }
}