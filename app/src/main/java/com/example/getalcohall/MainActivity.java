package com.example.getalcohall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb=findViewById(R.id.progressBar);
        pb.setMax(100);
        new Thread(){
            @Override
            public void run() {
                for(int i=1;i<=100;i++) {
                    pb.setProgress(i);
                    try {
                        sleep(30);
                    } catch (InterruptedException e) {

                    }
                }
                FirebaseAuth auth=FirebaseAuth.getInstance();
                if(auth.getCurrentUser()==null) {
                    startActivity(new Intent(MainActivity.this, Login.class));
                }else{
                    startActivity(new Intent(MainActivity.this, Home.class));
                }
                finish();
            }
        }.start();

    }
}