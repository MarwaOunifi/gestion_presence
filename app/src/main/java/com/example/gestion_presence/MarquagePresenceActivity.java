package com.example.gestion_presence;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MarquagePresenceActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EmployeAdapter adapter;
    private List<Employe> employeList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marquage_presence);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerViewEmployes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        employeList = dbHelper.getAllEmployes();
        adapter = new EmployeAdapter();


        recyclerView.setAdapter(adapter);
    }
}
