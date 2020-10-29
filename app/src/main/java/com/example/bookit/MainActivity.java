package com.example.bookit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText username;
    private EditText password;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();

        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
    if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
        Intent intent = new Intent(this, ScrollingActivity.class);
        String name = username.getText().toString();
//        intent.putExtra("passName",name);
        startActivity(intent);
    }
    else{
        Toast.makeText(this,"Wrong password or username", Toast.LENGTH_SHORT).show();
    }
    }


    public void setup(){
        username = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.pass);
        login = (Button) findViewById(R.id.log);
    }
}