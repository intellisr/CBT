package com.google.ceylonbus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public  SharedPreferences sharePref;
    public SharedPreferences.Editor editor;
    public EditText busNo;
    public String busnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        busNo = findViewById(R.id.regno);

        sharePref= PreferenceManager.getDefaultSharedPreferences(this);
        String driver=sharePref.getString("driver",null);
        String bus=sharePref.getString("busNo",null);
        busNo.setText(bus);

        if(driver != null){
            goDriverAccount();
        }

    }


    public void goBusTracker(View view){

        busnumber=busNo.getText().toString().toUpperCase();
        editor = sharePref.edit();
        editor.putString("busNo",busnumber);
        editor.apply();

        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }

    public void goDriverLogin(View view){
        Intent intent = new Intent(this,Driverlogin.class);
        startActivity(intent);
        finish();
    }

    public void goAdminLogin(View view){
        Intent intent = new Intent(this,AdminLogin.class);
        startActivity(intent);
    }

    public void goDriverAccount(){
        Intent intent = new Intent(this,DriverAccount.class);
        startActivity(intent);
        finish();
    }

}