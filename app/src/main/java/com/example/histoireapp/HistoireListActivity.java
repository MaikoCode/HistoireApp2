package com.example.histoireapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.histoireapp.R;
import com.example.histoireapp.HistoireAdapter;
import com.example.histoireapp.DatabaseHelper;
import com.example.histoireapp.Histoire;
import java.util.List;

public class HistoireListActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private RecyclerView recyclerView;
    private HistoireAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histoire_list);

        db = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadHistoires();

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddHistoireActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHistoires();
    }

    private void loadHistoires() {
        List<Histoire> histoires = db.getAllHistoires();
        adapter = new HistoireAdapter(this, histoires);
        recyclerView.setAdapter(adapter);
    }
}