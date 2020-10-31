package com.example.bookit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class DownloadActivity extends AppCompatActivity {

    static String link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        String link = getIntent().getStringExtra("hyperL");
        Log.d("link",link);

        new Dactivity().execute();
    }

    public class Dactivity extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids){
            try {
                Document html = Jsoup.connect(link).get();
                Element content = html.getElementsByTag("tr").first().getElementsByAttribute("href").first();
                String s = content.attr("href");
                Log.d("linkIs",s);
                Log.d("haha","hahaha");
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }
}