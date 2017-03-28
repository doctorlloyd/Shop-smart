package com.example.admin.shopsmart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.admin.shopsmart.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Thread splashScreen = new Thread(){
            public void run(){
                try{
                    sleep(3000);

                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(getBaseContext(),ShopsRecyclerView.class));
                    finish();
                }
            }
        };
        splashScreen.start();
    }
}
