package com.example.password_manager_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddDataPage extends AppCompatActivity {

    EditText etName, etPassowrd, etURL;
    Button btnAdd,btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (add() == -1 ){
                    return;
                }
                startActivity(new Intent(AddDataPage.this, MainActivity.class));
                finish();

            }
        });
        btnCancel=findViewById(R.id.Cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AddDataPage.this, MainActivity.class));
                finish();

            }
        });

    }
    private void init(){
        etName = findViewById(R.id.Username);
        etPassowrd = findViewById(R.id.InsertPassword);
        etURL = findViewById(R.id.URL);

        btnAdd = findViewById(R.id.Save);
    }

    private int add(){
        String name = etName.getText().toString().trim();
        String password = etPassowrd.getText().toString().trim();
        String url = etURL.getText().toString().trim();

        if(name.isEmpty() || password.isEmpty() || url.isEmpty())
        {
            Toast.makeText(AddDataPage.this, "Kindly Fill all Fields!", Toast.LENGTH_SHORT).show();
            return -1;
        }

        MyDatabase database = new MyDatabase(this);
        database.open();

        database.insertNote(name, password, url);

        database.close();

        return 0;
    }


}