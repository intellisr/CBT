package com.google.ceylonbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverRegister extends AppCompatActivity {

    public String Username;
    public String password;
    public EditText un;
    public EditText pw;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferencechild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_register);

        un=findViewById(R.id.un);
        pw=findViewById(R.id.pw);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Driver");
    }

    public void goRegister(View view){
        Username=un.getText().toString();
        password=pw.getText().toString();

        if(Username.equals("") || password.equals("")){
            Toast.makeText(getApplicationContext(),"Please Enter UserName and Password to Register Driver",Toast.LENGTH_LONG).show();
        }else{
            databaseReferencechild = databaseReference.child(Username);
            databaseReferencechild.setValue(password);
            Toast.makeText(getApplicationContext(),"Successfully Registered",Toast.LENGTH_LONG).show();
            un.setText("");
            pw.setText("");
        }
    }
}