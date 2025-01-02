package com.example.histoireapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.histoireapp.R;
import com.example.histoireapp.PaysAdapter;
import com.example.histoireapp.DatabaseHelper;
import com.example.histoireapp.Pays;
import java.util.List;

public class PaysListActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private RecyclerView recyclerView;
    private PaysAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pays_list);

        db = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadPays();

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddPaysActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPays();
    }

    private void loadPays() {
        List<Pays> paysList = db.getAllPays();
        adapter = new PaysAdapter(this, paysList);
        recyclerView.setAdapter(adapter);
    }
}