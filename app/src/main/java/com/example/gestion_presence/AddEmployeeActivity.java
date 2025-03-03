package com.example.gestion_presence;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEmployeeActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText editTextName, editTextSalary;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        db = new DatabaseHelper(this);
        editTextName = findViewById(R.id.editTextName);
        editTextSalary = findViewById(R.id.editTextSalary);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String salaryStr = editTextSalary.getText().toString();
                if (name.isEmpty() || salaryStr.isEmpty()) {
                    Toast.makeText(AddEmployeeActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }
                double salary = Double.parseDouble(salaryStr);
                boolean isInserted = db.addEmployee(name, salary);
                if (isInserted) {
                    Toast.makeText(AddEmployeeActivity.this, "Employé ajouté avec succès", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddEmployeeActivity.this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
