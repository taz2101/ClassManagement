package com.example.talkbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.talkbox.customerFoodPanel.HomeCustom;
import com.example.talkbox.customerFoodPanel.InformationCustom;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Menu extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.cust_home:
                fragment = new HomeCustom();
                break;

            case R.id.cust_info:
                fragment = new InformationCustom();
                break;
        }
        return loadcheffrafment(fragment);
    }

    private boolean loadcheffrafment(Fragment fragment) {
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
        return false;
    }
}