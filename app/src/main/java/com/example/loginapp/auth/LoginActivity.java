package com.example.loginapp.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.loginapp.R;
import com.example.loginapp.dashboard.AdminActivity;
import com.example.loginapp.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private Button loginButton, registerButton;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);

        loginButton.setOnClickListener(v -> attemptLogin());
        registerButton.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));

        // Check if user is already logged in
        if (userViewModel.isLoggedIn()) {
            startAdminActivity();
        }
    }

    private void attemptLogin() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        userViewModel.loginUser(email, password);
        userViewModel.getLoginResult().observe(this, success -> {
            if (success) {
                startAdminActivity();
            } else {
                Toast.makeText(this, "Login failed. Check credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startAdminActivity() {
        startActivity(new Intent(this, AdminActivity.class));
        finish();
    }
}
