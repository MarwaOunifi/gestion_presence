package com.example.gestion_presence;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.widget.Button;


public class EmployeAdapter extends RecyclerView.Adapter<EmployeAdapter.EmployeViewHolder> implements Filterable {

    private List<Employe> employeList;

    private List<Employe> employeListFull; // Liste complète pour la recherche

    public interface OnPresenceClickListener {
        void onPresenceClick(Employe employe);
        void onAbsenceClick(Employe employe);
    }

    private OnPresenceClickListener listener;

    public EmployeAdapter() {
        this.employeList = employeList;
        this.employeListFull = new ArrayList<>(employeList);
        this.listener = listener;
    }



    @NonNull
    @Override
    public EmployeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employe, parent, false);
        return new EmployeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeViewHolder holder, int position) {
        Employe employe = employeList.get(position);
        holder.nom.setText(employe.getNom());
        holder.poste.setText(employe.getPoste());
        holder.salaire.setText("Salaire: " + employe.getSalaire() + "€/h");

        holder.btnPresence.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPresenceClick(employe);
            }
        });

        holder.btnAbsence.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAbsenceClick(employe);
            }
        });



    }

    @Override
    public int getItemCount() {
        return employeList.size();
    }

    static class EmployeViewHolder extends RecyclerView.ViewHolder {
        TextView nom, poste, salaire;
        Button btnPresence, btnAbsence;

        public EmployeViewHolder(@NonNull View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.nomEmploye);
            poste = itemView.findViewById(R.id.posteEmploye);
            salaire = itemView.findViewById(R.id.salaireEmploye);

            btnPresence = itemView.findViewById(R.id.btnPresence);
            btnAbsence = itemView.findViewById(R.id.btnAbsence);
        }
    }

    @Override
    public Filter getFilter() {
        return employeFilter;
    }

    private Filter employeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Employe> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(employeListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Employe employe : employeListFull) {
                    if (employe.getNom().toLowerCase().contains(filterPattern)) {
                        filteredList.add(employe);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            employeList.clear();
            employeList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


}
