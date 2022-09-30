package com.example.absensikaryawan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    //hooks
    View satu, dua, tiga, empat, lima, enam;
    TextView a, slogan;
    //Animations
    Animation topAnimation, bottomAnimation, middleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);

        //hooks
        satu = findViewById(R.id.first_line);
        dua = findViewById(R.id.second_line);
        tiga = findViewById(R.id.third_line);
        empat = findViewById(R.id.fourth_line);
        lima = findViewById(R.id.fifth_line);
        enam = findViewById(R.id.sixth_line);

        a = findViewById(R.id.a);
        slogan = findViewById(R.id.tagLine);

        //memberi efek animasi
        satu.setAnimation(topAnimation);
        dua.setAnimation(topAnimation);
        tiga.setAnimation(topAnimation);
        empat.setAnimation(topAnimation);
        lima.setAnimation(topAnimation);
        enam.setAnimation(topAnimation);

        a.setAnimation(middleAnimation);
        slogan.setAnimation(bottomAnimation);

        //splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, UtamaActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}