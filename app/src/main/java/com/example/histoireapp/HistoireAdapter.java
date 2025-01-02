package com.example.histoireapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.histoireapp.R;
import com.example.histoireapp.Histoire;
import com.example.histoireapp.DatabaseHelper;
import java.util.List;

public class HistoireAdapter extends RecyclerView.Adapter<HistoireAdapter.ViewHolder> {
    private List<Histoire> histoires;
    private Context context;
    private DatabaseHelper db;

    public HistoireAdapter(Context context, List<Histoire> histoires) {
        this.context = context;
        this.histoires = histoires;
        this.db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_histoire, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Histoire histoire = histoires.get(position);

        holder.tvTitre.setText(histoire.getTitre());

        // Get author name
        String auteurName = "Auteur: " + db.getAuteur(histoire.getAuteurId()).toString();
        holder.tvAuteur.setText(auteurName);

        // Get category name
        String categorieName = "Catégorie: " + db.getCategorie(histoire.getCategorieId()).toString();
        holder.tvCategorie.setText(categorieName);

        holder.btnEdit.setOnClickListener(v -> {
            // Will be implemented
        });

        holder.btnDelete.setOnClickListener(v -> {
            db.deleteHistoire(histoire.getId());
            histoires.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return histoires.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitre, tvAuteur, tvCategorie;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitre = itemView.findViewById(R.id.tvTitre);
            tvAuteur = itemView.findViewById(R.id.tvAuteur);
            tvCategorie = itemView.findViewById(R.id.tvCategorie);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}