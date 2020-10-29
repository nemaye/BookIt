package com.example.bookit;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

import org.jsoup.Jsoup;

import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class NewActivity extends AppCompatActivity {
    private TextView text;
    boolean bool = false;
    boolean flag = false;
    boolean link_bool = false;
    String str = "";
    String name = "";
    String linkName_bool = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);


        setup();
        try {
            extract();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void extract() throws IOException {
        Document doc = Jsoup.connect("http://libgen.rs/search.php?req=quran&lg_topic=libgen&open=0&view=simple&res=25&phrase=1&column=def").get();
        Elements content = doc.select("a");

        for (Element src:content){
            String l = src.attr("href");

            if(src.ownText().equals("Extension")) {
                bool = true;
                continue;
            }

            linkName_bool = l;

            if(link_bool){
                System.out.println(linkName_bool);
            }

            link_bool = false;

            if(bool)
                if(src.ownText().equals("[1]")) {
                    System.out.println(str);
                    name = str;
                    link_bool = true;
                }

            str = src.ownText();

            if(flag){
                System.out.println(str);
                System.out.println("");
            }

            flag = false;

            if(str.equals("[edit]"))
                flag = true;

        }
    }

    private void setup() {

        Intent intent = getIntent();
        String msg = intent.getExtras().getString("passName");

        text = (TextView) findViewById(R.id.textView);
        text.setText(msg);


    }
}