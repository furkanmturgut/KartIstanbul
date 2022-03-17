package com.kunai.istanbulkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kunai.istanbulkart.home.HomeFragment;
import com.kunai.istanbulkart.home.PushFragment;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    String myTc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        frameLayout = findViewById(R.id.frameLayout);
        bottomNavigationView = findViewById(R.id.bottomNavMenu);

        Bundle bundle = getIntent().getExtras();
        myTc = bundle.getString("telNo");
        Log.e("Tel Home : ",myTc);

        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));

        homeFragmentTransfer();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeMenu:
                        homeFragmentTransfer();
                        break;
                    case R.id.notMenu:
                        fragmentTransfer(new PushFragment());
                        break;
                    case R.id.exitMenu:
                        break;
                }
                return true;
            }
        });

    }

    public  void fragmentTransfer(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

    public void homeFragmentTransfer(){
        Bundle bundle = new Bundle();
        bundle.putString("customer",myTc);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment  =  new HomeFragment();
        fragmentTransaction.replace(R.id.frameLayout,homeFragment,"myTelNo");
        homeFragment.setArguments(bundle);
        fragmentTransaction.commit();
    }
}