package com.example.gestion_presence;



import static androidx.core.content.ContextCompat.startActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText nomEmploye, posteEmploye, salaireHoraire;
    private Button ajouterEmploye, voirListePresences;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomEmploye = findViewById(R.id.nomEmploye);
        posteEmploye = findViewById(R.id.posteEmploye);
        salaireHoraire = findViewById(R.id.salaireHoraire);
        ajouterEmploye = findViewById(R.id.ajouterEmploye);
        voirListePresences = findViewById(R.id.voirListePresences);

        dbHelper = new DatabaseHelper(this);

        ajouterEmploye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterEmploye();
            }
        });

        voirListePresences.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListeEmployesActivity.class);
            startActivity(intent);
        });
    }

    private void ajouterEmploye() {
        String nom = nomEmploye.getText().toString().trim();
        String poste = posteEmploye.getText().toString().trim();
        String salaire = salaireHoraire.getText().toString().trim();

        if (nom.isEmpty() || poste.isEmpty() || salaire.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nom", nom);
        values.put("poste", poste);
        values.put("salaire", Double.parseDouble(salaire));

        long id = db.insert("employes", null, values);
        if (id != -1) {
            Toast.makeText(this, "Employé ajouté avec succès", Toast.LENGTH_SHORT).show();
            nomEmploye.setText("");
            posteEmploye.setText("");
            salaireHoraire.setText("");
        } else {
            Toast.makeText(this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show();
        }
    }
}