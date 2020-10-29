package com.example.bookit;

import android.content.Context;
import android.content.Intent;
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
    String linkName_bool = "";
    ArrayList<String> bookName = new ArrayList<String>();
    ArrayList<String> author = new ArrayList<String>();
    ArrayList<String> hLink = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_scrolling);

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
                hLink.add(linkName_bool);
            }

            link_bool = false;

            if(bool)
                if(src.ownText().equals("[1]")) {
                    bookName.add(str);
                    name = str;
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

    private void setup() {


        TableLayout tl = (TableLayout) findViewById(R.id.table);
        tl.setOrientation(LinearLayout.VERTICAL);


        TextView tv = new TextView(this);
        Log.d("size","Integer.toString(author.size())");
        tv.setText("my text");
        tl.addView(tv);

        for(int i=0; i<author.size()-1; i++){
            TableRow row = new TableRow(this);
//            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            TextView t1v = new TextView(this);
            t1v.setText("" + i);
            row.addView(t1v);
            tl.addView(row);
        }

    }
}