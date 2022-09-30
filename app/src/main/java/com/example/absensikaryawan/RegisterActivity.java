package com.example.absensikaryawan;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.absensikaryawan.api.ApiClient;
import com.example.absensikaryawan.api.ApiInterface;
import com.example.absensikaryawan.model.register.Register;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static int SPLASH_TIME_OUT = 1200;

    EditText etUsername, etPassword, etName, etNip;
    Button btnRegister;
    TextView tvLogin;
    String Username, Password, Name, Nip;
    ApiInterface apiInterface;

    AnimationDrawable animationDrawable;
    RelativeLayout rootLayout, animasiLayout;

    Animation animation, hilang;

    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etRegisterUsername);
        etPassword = findViewById(R.id.etRegisterPassword);
        etName = findViewById(R.id.etRegisterName);
        etNip = findViewById(R.id.etRegisterNip);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        tvLogin = findViewById(R.id.tvLoginHere);
        tvLogin.setOnClickListener(this);

        loading = findViewById(R.id.loading);

        rootLayout = findViewById(R.id.rootLayout);
        animasiLayout = findViewById(R.id.animasiLayout);

        //Perpindahan durasi setiap gambar
        animationDrawable = (AnimationDrawable) rootLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        //Memberikan Efek animasi pada Layout
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        animation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        animasiLayout.setAnimation(animation);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegister:
                Username = etUsername.getText().toString();
                Password = etPassword.getText().toString();
                Name = etName.getText().toString();
                Nip = etNip.getText().toString();
                if(Username.isEmpty()){
                    etUsername.setError("isi dulu usernamenya");
                }
                if(Password.isEmpty()){
                    etPassword.setError("isi dulu passwordnya");
                }
                if(Name.isEmpty()){
                    etName.setError("isi dulu Namenya");
                }
                if(Nip.isEmpty()){
                    etNip.setError("isi dulu NIPnya");
                }
                else {
                    register(Username, Password, Name, Nip);
                    loading.setVisibility(View.VISIBLE);
                    btnRegister.setVisibility(View.GONE);
                    tvLogin.setVisibility(View.GONE);
                }

                break;
            case R.id.tvLoginHere:
                hilang = AnimationUtils.loadAnimation(this, R.anim.hilang_ke_atas);
                animasiLayout.setAnimation(hilang);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },SPLASH_TIME_OUT);
                break;
        }
    }

    private void register(String username, String password, String name, String nip) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Register>
                call = apiInterface.registerResponse(username, password, name, nip);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {

                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    hilang = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hilang_ke_atas);
                    animasiLayout.setAnimation(hilang);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    },SPLASH_TIME_OUT);
                    loading.setVisibility(View.GONE);
                    btnRegister.setVisibility(View.VISIBLE);
                    tvLogin.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    btnRegister.setVisibility(View.VISIBLE);
                    tvLogin.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                btnRegister.setVisibility(View.VISIBLE);
                tvLogin.setVisibility(View.VISIBLE);
            }
        });

    }
}