package com.example.bookit;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;

public class DownloadActivity extends AppCompatActivity {
    ImageView img;
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
                Element imgURL = html.select("img").first();
                String img_url = content.absUrl("src");
                URL url = new URL(img_url);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                img = (ImageView) findViewById(R.id.imageView);
                img.setImageBitmap(bmp);
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