package com.google.ceylonbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AdminLogin extends AppCompatActivity {

    public String Username;
    public String password;
    public EditText un;
    public EditText pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        un=findViewById(R.id.un);
        pw=findViewById(R.id.pw);
    }

    public void goLogin(View view){
        Username=un.getText().toString();
        password=pw.getText().toString();

        if(Username.equals("sra") && password.equals("sra")){
            Intent intent = new Intent(this,DriverRegister.class);
            startActivity(intent);
            finish();
        }
    }
}