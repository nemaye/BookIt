package com.example.bookit;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class DownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        new Dactivity().execute();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ScrollingActivity.class));
    }

    public class Dactivity extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids){
            try {
                String link = getIntent().getStringExtra("hyperL");
                Log.d("link",link);
                Document html = Jsoup.connect(link).get();
                Element content = html.getElementsByTag("tr").first().getElementsByAttribute("href").first();

                final String s = content.attr("href");

                Button download = new Button(DownloadActivity.this);
                download = (Button) findViewById(R.id.getIt);
                download.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Log.d("download",s);
                        Uri uri = Uri.parse(s);
                        startActivity(new Intent(Intent.ACTION_VIEW,uri));
                    }
                });


            }
            catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }
}