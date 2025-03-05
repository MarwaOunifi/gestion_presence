package com.example.gestion_presence;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;

public class PresenceActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private Button btnFaireAppel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presence);

        calendarView = findViewById(R.id.calendarView);
        btnFaireAppel = findViewById(R.id.btnFaireAppel);

        btnFaireAppel.setOnClickListener(v -> {
            Intent intent = new Intent(PresenceActivity.this, MarquagePresenceActivity.class);
            startActivity(intent);
        });
    }
}
