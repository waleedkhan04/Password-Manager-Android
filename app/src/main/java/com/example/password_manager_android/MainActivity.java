package com.example.password_manager_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    TextView tvNumberLogin, tvNumberBin;
    FloatingActionButton btnAdd;

    LinearLayout layoutNotes, layoutProfile, layoutBin;


    @Override
    protected void onResume() {
        super.onResume();

        updateNumberOfNotes();
        updateNumberOfDeletedNotes();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        updateNumberOfNotes();
        updateNumberOfDeletedNotes();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddDataPage.class));

            }
        });

        layoutNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PasswordsPage.class));

            }
        });

        layoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // it will show a dialog where the user name will be displayed so that we know which user is currently
                // logged in, also has a option to logout by a button.

                View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.logout, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                Button btnLogout = dialogView.findViewById(R.id.btnLogout);
//                TextView tvName = dialogView.findViewById(R.id.tvName);
//
//                tvName.setText(getLoggedInID());

                btnLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        logoutUser();

                        startActivity(new Intent(MainActivity.this, LoginPage.class));
                        finish();

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        layoutBin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RecycleBinPage.class));
            }
        });
    }


    private void init() {
        tvNumberLogin = findViewById(R.id.tvNumberLogin);
        tvNumberBin = findViewById(R.id.tvNumberBin);

        btnAdd = findViewById(R.id.btnAdd);

        layoutNotes = findViewById(R.id.layoutNotes);
        layoutProfile = findViewById(R.id.layoutProfile);
        layoutBin = findViewById(R.id.layoutBin);

    }

    private void logoutUser() {
        MyDatabase database = new MyDatabase(this);

        database.open();
        database.logoutUser();
        database.close();
    }

//    private int getLoggedInID() {
//        MyDatabase database = new MyDatabase(this);
//
//        database.open();
//        int temp = database.getLoggedInID();
//        database.close();
//
//        return temp;
//
//    }

    private void updateNumberOfNotes() {
        MyDatabase database = new MyDatabase(this);

        database.open();
        tvNumberLogin.setText(database.numberOfPresentNotes() + "");
        database.close();
    }

    private void updateNumberOfDeletedNotes() {
        MyDatabase database = new MyDatabase(this);

        database.open();
        tvNumberBin.setText(database.numberOfDeletedNotes() + "");
        database.close();
    }
}
