package com.example.password_manager_android;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PasswordsPage extends AppCompatActivity {

    RecyclerView rvLoginNotes;
    PasswordAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle back button press here
                finish();
            }
        });

    }

    private void init() {

        rvLoginNotes = findViewById(R.id.rvLoginNotes);
        rvLoginNotes.setHasFixedSize(true);

        rvLoginNotes.setLayoutManager(new LinearLayoutManager(this));

        MyDatabase database = new MyDatabase(this);
        database.open();
        ArrayList<PasswordClass> notes = database.readAllNotes();
        database.close();

        rvLoginNotes.setAdapter(new PasswordAdapter(this, notes));
    }
}
