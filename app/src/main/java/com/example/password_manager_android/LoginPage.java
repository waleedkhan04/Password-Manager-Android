package com.example.password_manager_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class LoginPage extends AppCompatActivity {

    FragmentManager fragmentManager;
    Fragment loginFragment, registerFragment;
    View loginView, registerView;
    Button btnRegister_r, btnLogin_l, btnRegister_l,btnLogin_r;
    EditText etEmail_l, etPassword_l, etEmail_r, etPassword_r, etConfirmPassword_r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        if(isUserLoggedIn()){
            startActivity(new Intent(LoginPage.this, MainActivity.class));
            finish();

        }


        fragmentManager.beginTransaction()
                .hide(registerFragment)
                .commit();

        btnLogin_l.setOnClickListener(v -> {
            String email = etEmail_l.getText().toString().trim();
            String password = etPassword_l.getText().toString().trim();

            if ( email.isEmpty() || password.isEmpty() ) {
                Toast.makeText(LoginPage.this, "Please Fill the Form First" , Toast.LENGTH_SHORT).show();
                return;
            }

            int result = loginUser(email,password);
            if (result <= 0) {
                Toast.makeText(LoginPage.this, "Invalid Email/Username or Password!", Toast.LENGTH_SHORT).show();
                return;
            }

            startActivity(new Intent(LoginPage.this,MainActivity.class));
            finish();
        });

        btnRegister_l.setOnClickListener(v -> fragmentManager.beginTransaction()
                .hide(loginFragment)
                .show(registerFragment)
                .commit());

        btnRegister_r.setOnClickListener(v -> {
            String email = etEmail_r.getText().toString().trim();
            String password = etPassword_r.getText().toString();
            String rePassword = etConfirmPassword_r.getText().toString();

            if ( email.isEmpty() || password.isEmpty() || rePassword.isEmpty() ){
                Toast.makeText(LoginPage.this, "Please Fill the Form First", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(rePassword)){
                Toast.makeText(LoginPage.this, "Passwords do not match.Try Again!", Toast.LENGTH_SHORT).show();
                return;
            }

            registerUser(email, password);
            clear();

            fragmentManager.beginTransaction()
                    .hide(registerFragment)
                    .show(loginFragment)
                    .commit();
        });

    }

    private void init() {
        fragmentManager = getSupportFragmentManager();

        loginFragment = fragmentManager.findFragmentById(R.id.loginFragment);
        registerFragment = fragmentManager.findFragmentById(R.id.registerFragment);

        loginView = loginFragment.requireView();
        registerView = registerFragment.requireView();

        btnLogin_l = loginView.findViewById(R.id.btnLogin_l);
        btnRegister_l = loginView.findViewById(R.id.btnRegister_l);

        etEmail_l = loginView.findViewById(R.id.etEmail_l);
        etPassword_l = loginView.findViewById(R.id.etPassword_l);

        btnRegister_r = registerView.findViewById(R.id.btnRegister_r);
        btnLogin_r = registerView.findViewById(R.id.btnLogin_r);

        etEmail_r = registerView.findViewById(R.id.etEmail_r);
        etPassword_r = registerView.findViewById(R.id.etPassword_r);
        etConfirmPassword_r = registerView.findViewById(R.id.etConfirmPassword_r);

        if (btnLogin_r!= null) {
            btnLogin_r.setOnClickListener(v -> {
                fragmentManager.beginTransaction()
                        .hide(registerFragment)
                        .show(loginFragment)
                        .commit();
            });
        }
    }

    private void clear() {
        etEmail_l.setText("");
        etPassword_l.setText("");

        etEmail_r.setText("");
        etPassword_r.setText("");
        etConfirmPassword_r.setText("");
    }

    private void registerUser(String email, String password) {
        MyDatabase database = new MyDatabase(this);
        database.open();
        database.insertLogin(email, password);
        database.close();
    }

    private int loginUser(String email,String password) {
        MyDatabase database = new MyDatabase(this);
        database.open();
        int result = database.loginUser(email,password);
        database.close();
        return result;
    }

    private boolean isUserLoggedIn() {
        MyDatabase database = new MyDatabase(this);
        database.open();
        boolean temp = database.isUserLoggedIn();
        database.close();
        return temp;
    }
}
