package com.example.gestion_presence;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ListeEmployesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EmployeAdapter adapter;
    private List<Employe> employeList;
    private SearchView searchView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_employes);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);
        employeList = getAllEmployes();
        adapter = new EmployeAdapter();
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private List<Employe> getAllEmployes() {
        List<Employe> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM employes", null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new Employe(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getDouble(3)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}


