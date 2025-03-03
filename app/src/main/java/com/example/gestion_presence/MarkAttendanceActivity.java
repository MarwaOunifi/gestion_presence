package com.example.gestion_presence;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MarkAttendanceActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText editTextDays, editTextHours;
    Spinner spinnerType;
    Button btnSaveAttendance;
    int employeeId;
    String attendanceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        db = new DatabaseHelper(this);
        editTextDays = findViewById(R.id.editTextDays);
        editTextHours = findViewById(R.id.editTextHours);
        spinnerType = findViewById(R.id.spinnerType);
        btnSaveAttendance = findViewById(R.id.btnSaveAttendance);

        employeeId = getIntent().getIntExtra("employee_id", -1);
        if (employeeId == -1) {
            Toast.makeText(this, "Erreur: Employé non trouvé", Toast.LENGTH_SHORT).show();
            finish();
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.attendance_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                attendanceType = parentView.getItemAtPosition(position).toString();
                if (attendanceType.equals("Par Jour")) {
                    editTextHours.setEnabled(false);
                    editTextDays.setEnabled(true);
                } else if (attendanceType.equals("Par Heure")) {
                    editTextHours.setEnabled(true);
                    editTextDays.setEnabled(false);
                } else {
                    editTextHours.setEnabled(false);
                    editTextDays.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        btnSaveAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int days = editTextDays.isEnabled() ? Integer.parseInt(editTextDays.getText().toString()) : 0;
                int hours = editTextHours.isEnabled() ? Integer.parseInt(editTextHours.getText().toString()) : 0;
                boolean isInserted = db.markAttendance(employeeId, attendanceType, days, hours);
                if (isInserted) {
                    Toast.makeText(MarkAttendanceActivity.this, "Présence enregistrée", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(MarkAttendanceActivity.this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
