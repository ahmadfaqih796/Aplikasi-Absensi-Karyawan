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
import com.example.absensikaryawan.model.login.Login;
import com.example.absensikaryawan.model.login.LoginData;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static int SPLASH_TIME_OUT = 1300;

    EditText etUsername, etPassword;
    Button btnLogin;
    String Username, Password;
    TextView tvRegister;
    ApiInterface apiInterface;
    SessionManager sessionManager;
    ProgressBar loading;

    AnimationDrawable animationDrawable;
    RelativeLayout rootLayout, animasiLayout;

    Animation animation, hilang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        tvRegister = findViewById(R.id.tvCreateAccount);
        tvRegister.setOnClickListener(this);

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
        animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        animasiLayout.setAnimation(animation);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnLogin:
                Username = etUsername.getText().toString();
                Password = etPassword.getText().toString();
                if(Username.isEmpty()){
                    etUsername.setError("Masukkan emailnya anjim");
                }
                if(Password.isEmpty()){
                    etPassword.setError("Masukkan Passwordnya anjim");
                }
                else {
                    login(Username, Password);
                    loading.setVisibility(View.VISIBLE);
                    btnLogin.setVisibility(View.GONE);
                }

                break;
            case R.id.tvCreateAccount:
                hilang = AnimationUtils.loadAnimation(this, R.anim.hilang_ke_bawah);
                animasiLayout.setAnimation(hilang);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },SPLASH_TIME_OUT);
                break;
        }
    }

    private void login(String username, String password) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Login> loginCall = apiInterface.loginResponse(username,password);
        loginCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){

                    // Ini untuk menyimpan sesi
                    sessionManager = new SessionManager(LoginActivity.this);
                    LoginData loginData = response.body().getLoginData();
                    sessionManager.createLoginSession(loginData);

                    //Ini untuk pindah
                    Toast.makeText(LoginActivity.this, response.body().getLoginData().getName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, SplashScreenActivity.class);
                    startActivity(intent);
                    loading.setVisibility(View.GONE);
                    btnLogin.setVisibility(View.VISIBLE);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    btnLogin.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);
            }
        });
    }
}