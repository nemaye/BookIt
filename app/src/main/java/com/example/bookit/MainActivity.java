package com.example.bookit;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private RadioGroup rGroup;
    private Button search;
    private EditText text;
    private static String select;
    private String name;
    private RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();

        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) group.findViewById(checkedId);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = rGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(MainActivity.this, "ERROR: select something", Toast.LENGTH_SHORT).show();
                }
                else{
                    name = (String) radioButton.getText();
                    Intent intent = new Intent(MainActivity.this,ScrollingActivity.class);
                    intent.putExtra("data", name);
                    intent.putExtra("text", String.valueOf(text.getText()));
                    startActivity(intent);
                }
            }
        });

    }


    public void setup(){
        rGroup = (RadioGroup) findViewById(R.id.radioGroup);
        text = (EditText) findViewById(R.id.string);
        search = (Button) findViewById(R.id.search);
    }

}