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
import com.example.histoireapp.Categorie;
import com.example.histoireapp.DatabaseHelper;
import java.util.List;

public class CategorieAdapter extends RecyclerView.Adapter<CategorieAdapter.ViewHolder> {
    private List<Categorie> categories;
    private Context context;
    private DatabaseHelper db;

    public CategorieAdapter(Context context, List<Categorie> categories) {
        this.context = context;
        this.categories = categories;
        this.db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_categorie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Categorie categorie = categories.get(position);
        holder.tvNom.setText(categorie.getNom());

        holder.btnEdit.setOnClickListener(v -> {
            // Will be implemented
        });

        holder.btnDelete.setOnClickListener(v -> {
            db.deleteCategorie(categorie.getId());
            categories.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNom;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNom = itemView.findViewById(R.id.tvNom);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}