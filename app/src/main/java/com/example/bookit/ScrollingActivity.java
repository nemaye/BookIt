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
    String str = "";
    String name = "";
    String test = "q";
    String linkName_bool = "";
    static ArrayList<String> bookName = new ArrayList<String>();
    static ArrayList<String> author = new ArrayList<String>();
    static ArrayList<String> hLink = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_scrolling);

        new Connection().execute();
        setup();

    }

    public class Connection extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
                try
                {
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
        }
//    @SuppressLint("SetTextI18n")
    private void setup() {


        TableLayout tl = (TableLayout) findViewById(R.id.table);
        tl.setOrientation(LinearLayout.VERTICAL);


        TextView tv = new TextView(this);
        Log.d("size","Integer.toString(author.size())");
        tv.setText("my text");
        tl.addView(tv);



        for(int i=0; i<author.size()-1; i++){
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView t1v = new TextView(this);
            t1v.setText(author.get(i));
            row.addView(t1v);
            tl.addView(row,i);
        }

    }
}