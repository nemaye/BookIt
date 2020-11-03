package com.example.bookit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.View;
import android.view.LayoutInflater;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ScrollingActivity extends AppCompatActivity {
    private TextView text;
    boolean bool = false;
    boolean flag = false;
    boolean link_bool = false;
    static int i = 0;
    String str = "";
    String name = "";
    String test = "q";
    String linkName_bool = "";
    static ArrayList<String> bookName = new ArrayList<String>();
    static ArrayList<String> author = new ArrayList<String>();
    static ArrayList<String> hLink = new ArrayList<String>();
    public Connection task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_scrolling);

        Intent intent = getIntent();
        String s = intent.getExtras().getString("passName");
        Log.d("sss",s);

        new Connection().execute();
    }

    public class Connection extends AsyncTask<Void, Void, Void> {           //was needed to remove unable to start activity due to network error... Async runs the code on the main thread in bg

        @Override
        protected Void doInBackground(Void... voids) {
                try
                {
                    Document doc = Jsoup.connect("http://libgen.rs/search.php?req=social&lg_topic=libgen&open=0&view=simple&res=25&phrase=1&column=def").get();
                    Elements content = doc.select("a");

                    for (Element src:content){
                        String l = src.attr("href");

                        if(src.ownText().equals("Extension")) {
                            bool = true;
                            continue;
                        }

                        linkName_bool = l;

                        if(link_bool){
                            hLink.add(linkName_bool);
                        }

                        link_bool = false;

                        if(bool)
                            if(src.ownText().equals("[1]")) {
                                bookName.add(str);
                                name = str;
                                test = "test";
                                link_bool = true;
                            }

                        str = src.ownText();

                        if(flag){
                            author.add(str);
                        }

                        flag = false;

                        if(str.equals("[edit]"))
                            flag = true;

                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            return null;
            }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setup();
        }

        public void cancel() {
        }
    }
//    @SuppressLint("SetTextI18n")
    private void setup(){

        TableLayout tl = (TableLayout) findViewById(R.id.table);
        tl.setOrientation(LinearLayout.VERTICAL);


        TextView tv = new TextView(this);
        Log.d("size","Integer.toString(author.size())");
        tv.setText("my text");
        tl.addView(tv);



        for(i=0; i<author.size()-1; i++){
            LinearLayout row = new LinearLayout(this);
//            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
//            row.setLayoutParams(lp);
            Button author_name = new Button(this);
            TextView book_name = new TextView(this);
            TextView border = new TextView(this);
            border.setText("_________\n");
            book_name.setText(String.format("%s\n", bookName.get(i)));
            author_name.setText(String.format("%s\n", author.get(i)));
            row.addView(author_name);
            row.addView(book_name);
//            row.addView(border);
            tl.addView(row);
            author_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ScrollingActivity.this, DownloadActivity.class);
                    intent.putExtra("hyperL",hLink.get(i));
                    Log.i("TAG", hLink.get(i));
                    startActivity(intent);
                }
            });

        }

    }
}