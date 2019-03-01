package com.example.rhkdg.sharethetrip;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 2000); // 2000ms = 2sec
    }

    private class splashhandler implements Runnable{
        public void run(){
            startActivity(new Intent(getApplication(), SignInActivity.class)); //After loding,To SingInActivity
            SplashActivity.this.finish();
        }
    }

    @Override
    public void onBackPressed() {
    }
}
