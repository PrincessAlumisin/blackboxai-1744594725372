package com.example.loginapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.loginapp.auth.LoginActivity;
import com.example.loginapp.dashboard.AdminActivity;
import com.example.loginapp.utils.SharedPrefManager;

public class MainActivity extends AppCompatActivity {
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefManager = new SharedPrefManager(this);

        if (sharedPrefManager.isLoggedIn()) {
            startActivity(new Intent(this, AdminActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }
}
