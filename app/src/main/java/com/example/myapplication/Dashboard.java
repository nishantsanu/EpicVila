package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.i("Info", "Reached to created dashBoard");
        setContentView(R.layout.activity_dashboard);
//        Log.i("Info", "Reached to created dashBoard1");
        BottomNavigationView navView = findViewById(R.id.nav_view);
//        Log.i("Info", "Reached to created dashBoard2");
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
//        Log.i("Info", "Reached to created dashBoard3");
          NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        Log.i("Info", "Reached to created dashBoard4");
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//          NavigationUI.navigateUp(navController,appBarConfiguration);
  //        Log.i("Info", "Reached to created dashBoard5");
          NavigationUI.setupWithNavController(navView, navController);

    }


}