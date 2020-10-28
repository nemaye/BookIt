package com.example.bookit;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NewActivity extends AppCompatActivity {
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        setup();
    }


    private void setup() {

        Intent intent = getIntent();
        String msg = intent.getExtras().getString("passName");

        text = (TextView) findViewById(R.id.textView);
        text.setText(msg);
    }
}