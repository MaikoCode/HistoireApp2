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
import com.example.histoireapp.Auteur;
import com.example.histoireapp.DatabaseHelper;
import java.util.List;

public class AuteurAdapter extends RecyclerView.Adapter<AuteurAdapter.ViewHolder> {
    private List<Auteur> auteurs;
    private Context context;
    private DatabaseHelper db;

    public AuteurAdapter(Context context, List<Auteur> auteurs) {
        this.context = context;
        this.auteurs = auteurs;
        this.db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_auteur, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Auteur auteur = auteurs.get(position);

        holder.tvNomPrenom.setText(auteur.getPrenom() + " " + auteur.getNom());

        // Get pays name
        String paysName = "Pays: " + db.getPays(auteur.getPaysId()).getNom();
        holder.tvPays.setText(paysName);

        holder.btnEdit.setOnClickListener(v -> {
            // Will be implemented
        });

        holder.btnDelete.setOnClickListener(v -> {
            db.deleteAuteur(auteur.getId());
            auteurs.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return auteurs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomPrenom, tvPays;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomPrenom = itemView.findViewById(R.id.tvNomPrenom);
            tvPays = itemView.findViewById(R.id.tvPays);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}