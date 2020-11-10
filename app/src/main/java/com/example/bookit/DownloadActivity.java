package com.example.bookit;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import com.squareup.picasso.Picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.style.IconMarginSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import com.bumptech.glide.Glide;
import java.net.URL;

public class DownloadActivity extends AppCompatActivity {
    ImageView img;
    String img_url;
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
                img_url = imgURL.absUrl("src");
                Log.d("imgURL",img_url);


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

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            img = findViewById(R.id.imageView);
            Glide.with(DownloadActivity.this).load(img_url).override(400,400).into(img);
        }
    }
}