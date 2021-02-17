package com.google.ceylonbus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Driverlogin extends AppCompatActivity {

    public String Username;
    public String password;
    public EditText un;
    public EditText pw;
    private DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverlogin);

        un=findViewById(R.id.un);
        pw=findViewById(R.id.pw);

        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public void goLoginDriver(View view){
        Username=un.getText().toString();
        password=pw.getText().toString();

        if(Username.equals("") || password.equals("")){
            Toast.makeText(getApplicationContext(),"Please Enter UserName and Password to Login",Toast.LENGTH_LONG).show();
        }else{
            firebaseDatabase.child("Driver").child(Username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),"There is some problem with your connection",Toast.LENGTH_LONG).show();
                    }
                    else {
                        String passwordFB=String.valueOf(task.getResult().getValue());
                        Log.i("*****", passwordFB);
                        if(password.equals(passwordFB)){
                            Toast.makeText(getApplicationContext(),"Successfully Login to : "+ Username,Toast.LENGTH_LONG).show();
                            SharedPreferences sharePref= PreferenceManager.getDefaultSharedPreferences(Driverlogin.this);
                            SharedPreferences.Editor editor = sharePref.edit();
                            editor.putString("driver",Username);
                            editor.putString("bus","Register No");
                            editor.apply();
                            goDriverAccount();
                        }else{
                            Toast.makeText(getApplicationContext(),"Please Enter Valid Username or Password",Toast.LENGTH_LONG).show();
                        }

                    }
                }
            });
        }

    }

    public void goDriverAccount(){
        Intent intent = new Intent(this,DriverAccount.class);
        startActivity(intent);
        finish();
    }
}