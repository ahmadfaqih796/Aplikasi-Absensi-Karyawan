package com.example.absensikaryawan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class UtamaActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView sejarah, visi_misi, absen, about;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);

        sejarah = findViewById(R.id.sejarah);
        visi_misi = findViewById(R.id.visi_misi);
        absen = findViewById(R.id.absen);
        about = findViewById(R.id.about);

        sejarah.setOnClickListener(this);
        absen.setOnClickListener(this);
        about.setOnClickListener(this);

        sessionManager = new SessionManager(UtamaActivity.this);
        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }
    }

    private void moveToLogin() {
        Intent intent = new Intent(UtamaActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionLogout:
                sessionManager.logoutSession();
                moveToLogin();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sejarah:
                //Intent sejarah = new Intent(this,SejarahActivity.class);
                //startActivity(sejarah);
                break;
            case R.id.absen:
                Intent absen = new Intent(this,AbsenActivity.class);
                startActivity(absen);
                break;
            case R.id.about:
                Intent about = new Intent(this,MainActivity.class);
                startActivity(about);
                break;
        }

    }
}