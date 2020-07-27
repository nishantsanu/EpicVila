package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity  {

    public void getLocationPage(View view){
        Log.i("Info","getting location");
        //calling another activity
        Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(intent);
        //setContentView(R.layout.activity_maps);
        //accesing user location

    }

    public void proceedToVerify(View view){
        Log.i("Info","Proceeded to verify");
        setContentView(R.layout.enter_your_details);
    }
    public void numberEntered(View view){
        Log.i("Info","Number Entered");
        setContentView(R.layout.number_verification);
    }
    public void getStartedClicked(View view){
        Log.i("Info","Button Clicked!");
        setContentView(R.layout.enter_mobile_number);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    }

}