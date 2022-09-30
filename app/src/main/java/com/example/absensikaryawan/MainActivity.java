package com.example.absensikaryawan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView etUsername, etName, etNip;
    SessionManager sessionManager;
    String username, name, nip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(MainActivity.this);

        etUsername = findViewById(R.id.etMainUsername);
        etName = findViewById(R.id.etMainName);
        etNip = findViewById(R.id.etMainNip);

        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        name = sessionManager.getUserDetail().get(SessionManager.NAME);
        nip = sessionManager.getUserDetail().get(SessionManager.NIP);

        etUsername.setText(username);
        etName.setText(name);
        etNip.setText(nip);


    }
}