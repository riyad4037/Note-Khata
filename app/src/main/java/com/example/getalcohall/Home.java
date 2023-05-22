package com.example.getalcohall;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    ViewPager vp;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout=findViewById(R.id.drawerlayout);
        navigationView= findViewById(R.id.navigationview);
        toolbar=findViewById(R.id.toolbar);

//        setSupportActionBar(toolbar);
         ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);
         drawerLayout.addDrawerListener(toggle);

         toggle.syncState();
         navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 if(item.getItemId()==R.id.logout_menu){
                     FirebaseAuth.getInstance().signOut();
                     finish();
                     startActivity(new Intent(Home.this,MainActivity.class));
                 }
                 if(item.getItemId()==R.id.drawerAccount){
                     startActivity(new Intent(Home.this,ProfileInfo.class));
                 }
                 drawerLayout.closeDrawer(GravityCompat.START);
                 return true;
             }
         });

        vp=findViewById(R.id.home_viewpager);

        findViewById(R.id.drawerAccount);


        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {


            @NonNull
            @Override
            public Fragment getItem(int position) {
                if(position==0) {
                    return new MainView(Home.this);
                }else{
                    return new Profile();
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        });


        TabLayout tl=findViewById(R.id.home_tablayout);
        tl.setupWithViewPager(vp);
        tl.getTabAt(0).setIcon(R.drawable.ic_baseline_home_24);
        tl.getTabAt(1).setIcon(R.drawable.ic_baseline_account_circle_24);


    }
}