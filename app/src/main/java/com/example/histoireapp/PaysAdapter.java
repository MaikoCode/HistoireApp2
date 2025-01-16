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
import com.example.histoireapp.Pays;
import com.example.histoireapp.DatabaseHelper;
import java.util.List;
import android.content.Intent;

public class PaysAdapter extends RecyclerView.Adapter<PaysAdapter.ViewHolder> {
    private List<Pays> paysList;
    private Context context;
    private DatabaseHelper db;

    public PaysAdapter(Context context, List<Pays> paysList) {
        this.context = context;
        this.paysList = paysList;
        this.db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pays, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pays pays = paysList.get(position);
        holder.tvNom.setText(pays.getNom());

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddPaysActivity.class);
            intent.putExtra("PAYS_ID", pays.getId());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            db.deletePays(pays.getId());
            paysList.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return paysList.size();
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