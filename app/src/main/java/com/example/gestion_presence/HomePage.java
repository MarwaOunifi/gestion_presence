package com.example.gestion_presence;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private LinearLayout presenceLayout, statistiqueLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        // Initialiser les vues
        tabLayout = findViewById(R.id.tabLayout);
        recyclerView = findViewById(R.id.membersRecyclerView);
        presenceLayout = findViewById(R.id.presenceLayout);
        statistiqueLayout = findViewById(R.id.statistiqueLayout);

        // RecyclerView pour afficher la liste des membres
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Adapte le RecyclerView avec un adapter (que tu dois créer pour les membres)
        recyclerView.setAdapter(new EmployeAdapter());

        // Gérer les changements d'onglet
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Masquer toutes les vues
                recyclerView.setVisibility(View.GONE);
                presenceLayout.setVisibility(View.GONE);
                statistiqueLayout.setVisibility(View.GONE);

                // Afficher la vue correspondant à l'onglet sélectionné
                switch (tab.getPosition()) {
                    case 0:
                        recyclerView.setVisibility(View.VISIBLE); // Affiche les membres
                        break;
                    case 1:
                        presenceLayout.setVisibility(View.VISIBLE); // Affiche la présence
                        break;
                    case 2:
                        statistiqueLayout.setVisibility(View.VISIBLE); // Affiche les statistiques
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Optionnel, si tu veux gérer l'événement quand un onglet est désélectionné
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Optionnel, si tu veux gérer l'événement quand un onglet est reselectionné
            }
        });

        // Sélectionner l'onglet par défaut (par exemple, Membres)
        tabLayout.getTabAt(0).select();
    }

    // Méthode pour obtenir la liste des employés (remplace par ta logique)
    private List<Employe> getEmployes() {
        // Récupère la liste des employés depuis ta base de données ou autre source
        return new ArrayList<>();
    }
}
