package com.google.ceylonbus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DriverAccount extends AppCompatActivity {

    public TextView hello;
    public String hi;
    public EditText busNo;
    public SharedPreferences sharePref;
    public SharedPreferences.Editor editor;
    private static final int PERMISSIONS_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_account);
        hello=findViewById(R.id.textView);
        sharePref= PreferenceManager.getDefaultSharedPreferences(this);
        String driver=sharePref.getString("driver",null);
        String bus=sharePref.getString("bus",null);
        hi="Hello "+ driver;
        hello.setText(hi);
        busNo=findViewById(R.id.regnobus);
        busNo.setText(bus);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startTrackerService();
        } else {
            Toast.makeText(this, "Please enable location services to allow GPS tracking", Toast.LENGTH_SHORT).show();
        }
    }

    private void startTrackerService() {
        startService(new Intent(this, LocationServices.class));
        Toast.makeText(this, "GPS tracking enabled", Toast.LENGTH_SHORT).show();
    }

    public void stopTrackerService(View view) {
        stopService(new Intent(this, LocationServices.class));
        Toast.makeText(this, "GPS tracking disabled", Toast.LENGTH_SHORT).show();
    }

    public void shareLocation(View view){

        String busnumber = busNo.getText().toString().toUpperCase();
        Log.i("*****", busnumber);

        editor = sharePref.edit();
        editor.putString("bus",busnumber);
        editor.apply();

        //Check whether GPS tracking is enabled//
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Please enable location services to allow GPS tracking", Toast.LENGTH_SHORT).show();
        }

        //Check whether this app has access to the location permission//
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        //If the location permission has been granted, then start the TrackerService//
        if (permission == PackageManager.PERMISSION_GRANTED) {
            startTrackerService();
        } else {
            //If the app doesn’t currently have access to the user’s location, then request access//
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST);
        }
    }


    public void logout(View view){
        sharePref= PreferenceManager.getDefaultSharedPreferences(this);
        sharePref.edit().clear().commit();

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}