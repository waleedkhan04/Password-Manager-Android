package com.example.password_manager_android;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleBinPage extends AppCompatActivity {

    RecyclerView rvBinNotes;
    TextView tvDeleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recyle_data);
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

        tvDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAll();
                finish();
            }
        });

    }

    private void init(){
        tvDeleteAll = findViewById(R.id.tvDeleteAll);

        rvBinNotes = findViewById(R.id.rvBinNotes);
        rvBinNotes.setHasFixedSize(true);

        rvBinNotes.setLayoutManager(new LinearLayoutManager(this));

        MyDatabase database = new MyDatabase(this);
        database.open();
        ArrayList<PasswordClass> notes = database.readAllDeletedNotes();
        database.close();

        rvBinNotes.setAdapter(new DeletedPasswordAdapter(this, notes));

        TextView noItemsTextView = findViewById(R.id.tvNoItems);
        if (notes.size() == 0) {
            noItemsTextView.setVisibility(View.VISIBLE);
            rvBinNotes.setVisibility(View.GONE);
        } else {
            noItemsTextView.setVisibility(View.GONE);
            rvBinNotes.setVisibility(View.VISIBLE);
        }

    }


    private void deleteAll(){
        MyDatabase database = new MyDatabase(this);
        database.open();
        database.deleteNotes();
        database.close();
    }
}