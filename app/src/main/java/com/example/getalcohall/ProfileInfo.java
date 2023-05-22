package com.example.getalcohall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class ProfileInfo extends AppCompatActivity {

    FragmentManager fragmentManager;
    Profile profile = new Profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
        

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.Frame_layout,profile).show(profile).commit();


    }
}