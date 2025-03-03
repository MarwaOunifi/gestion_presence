package com.example.gestion_presence;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    ListView listView;
    Button btnAddEmployee;
    ArrayList<String> employeeList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);
        btnAddEmployee = findViewById(R.id.btnAddEmployee);
        employeeList = new ArrayList<>();

        loadEmployees();

        btnAddEmployee.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEmployeeActivity.class);
            startActivity(intent);
        });

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(MainActivity.this, MarkAttendanceActivity.class);
            intent.putExtra("employee_name", employeeList.get(i));
            startActivity(intent);
        });
    }

    private void loadEmployees() {
        Cursor cursor = db.getAllEmployees();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Aucun employé trouvé", Toast.LENGTH_SHORT).show();
            return;
        }
        while (cursor.moveToNext()) {
            employeeList.add(cursor.getString(1));
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employeeList);
        listView.setAdapter(adapter);
    }
}
