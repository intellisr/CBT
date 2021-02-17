package com.google.ceylonbus;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public GoogleMap mMap;
    public DatabaseReference databaseReference;
    public DatabaseReference databaseReferenceUnits;
    public SharedPreferences sharePref;
    public String bus;
    public FirebaseDatabase database;
    public Marker BusMarker;
    private DatabaseReference firebaseDatabase;
    public int first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        first = 0;
        sharePref= PreferenceManager.getDefaultSharedPreferences(this);
        bus=sharePref.getString("busNo",null);
        Log.i("*****", "Location changed1"+bus);

        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

            database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference("BusLocation");
            databaseReferenceUnits = databaseReference.child(bus);
            databaseReferenceUnits.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    BusLocation busLocation = dataSnapshot.getValue(BusLocation.class);
                    if (busLocation != null) {
                        if(first == 1) {
                            Log.i("*****", "Location changed" + busLocation.getLat());
                            LatLng bus = new LatLng(busLocation.getLat(), busLocation.getLon());
                            BusMarker.setPosition(bus);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bus,15));
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Invalid Bus No ",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i("*****", "Location on map");
        mMap = googleMap;
        firebaseDatabase.child("BusLocation").child(bus).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"There is some problem with your connection",Toast.LENGTH_LONG).show();
                }
                else {
                    DataSnapshot data = task.getResult();
                    BusLocation busLocation = data.getValue(BusLocation.class);
                    if(busLocation != null) {
                        Log.i("*****", "Location changed" + busLocation.getLat());
                        LatLng bus = new LatLng(busLocation.getLat(), busLocation.getLon());
                        BusMarker = mMap.addMarker(new MarkerOptions().position(bus).title(busLocation.getBus()).snippet(busLocation.getDriver()));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bus, 15));
                        first = 1;
                    }else{
                        Toast.makeText(getApplicationContext(),"Invalid Bus No ",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

}