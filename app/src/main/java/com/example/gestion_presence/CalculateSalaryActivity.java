package com.example.gestion_presence;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CalculateSalaryActivity extends AppCompatActivity {
    DatabaseHelper db;
    TextView textViewSalary;
    int employeeId;
    double baseSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_salary);

        db = new DatabaseHelper(this);
        textViewSalary = findViewById(R.id.textViewSalary);
        employeeId = getIntent().getIntExtra("employee_id", -1);

        if (employeeId == -1) {
            Toast.makeText(this, "Erreur: Employé non trouvé", Toast.LENGTH_SHORT).show();
            finish();
        }

        calculateSalary();
    }

    private void calculateSalary() {
        Cursor employeeCursor = db.getAllEmployees();
        if (employeeCursor.moveToFirst()) {
            baseSalary = employeeCursor.getDouble(2);
        }

        Cursor attendanceCursor = db.getAttendance(employeeId);
        double totalSalary = 0;
        while (attendanceCursor.moveToNext()) {
            String type = attendanceCursor.getString(2);
            int days = attendanceCursor.getInt(3);
            int hours = attendanceCursor.getInt(4);

            if (type.equals("Par Jour")) {
                totalSalary += days * (baseSalary / 30);
            } else if (type.equals("Par Heure")) {
                totalSalary += hours * (baseSalary / (30 * 8));
            }
        }

        textViewSalary.setText("Salaire total: " + totalSalary + " DT");
    }
}

