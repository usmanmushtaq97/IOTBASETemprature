package com.example.nodemcucommunication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import com.google.firebase.auth.FirebaseAuth;
import static com.example.nodemcucommunication.Constants.SPLASH_TIME;
public class SplashScreens extends AppCompatActivity {
    Handler handler;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screens);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            handler = new Handler();
            handler.postDelayed(() -> {
                intent = new Intent(SplashScreens.this, MainActivity.class);
                startActivity(intent);
                finish();
            }, SPLASH_TIME);
        }
        else {
            intent = new Intent(SplashScreens.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}