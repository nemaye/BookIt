package com.example.bookit;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.view.View;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class ScrollingActivity extends AppCompatActivity {
    static int i = 0;
    String url;
    String data;
    String passText;
    static String author;
    static ArrayList<String> authorName = new ArrayList<String>(100);
    static ArrayList<String> authorList = new ArrayList<String>(100);
    static ArrayList<String> bookName = new ArrayList<String>(100);
    static ArrayList<String> link = new ArrayList<String>(100);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_scrolling);

        Intent intent = getIntent();
        data = intent.getExtras().getString("data");
        passText = intent.getExtras().getString("text");
        data = data.replace(" ","+");
        passText = passText.replace(" ","+");
        new Connection().execute();
    }

    public class Connection extends AsyncTask<Void, Void, Void> {           //was needed to remove unable to start activity due to network error... Async runs the code on the main thread in bg

        @Override
        protected Void doInBackground(Void... voids) {
                try
                {
                    if(data.equals("Title")){
                        url = "http://libgen.rs/search.php?req="+passText+"&lg_topic=libgen&open=0&view=simple&res=25&phrase=1&column=" + "Title";
                    }
                    else {
                        url = "http://libgen.rs/search.php?req="+passText+"&lg_topic=libgen&open=0&view=simple&res=25&phrase=1&column=" + "Author";
                    }

                    int count = 11;
                    String sub = "@11";
                    Document doc = Jsoup.connect(url).get();
                    Elements content = doc.select("a");

                    for(Element src:content){
                        if(src.text().equals("[1]")) {
                            link.add(src.attr("href"));                     //link
                            count++;
                        }
                        if(src.attr("href").contains("column=author")) {    //author
                            author = src.text()+"@"+Integer.toString(count);
                            authorList.add(author);
                        }

                        if(src.attr("href").contains("book/index")){        //bookName
                            bookName.add(src.text());
                        }
                    }
                    run();

                }
                catch (IOException e){
                    e.printStackTrace();
                }
            return null;
            }

        private void run() {
            String curr;
            String next;
            String sub;
            String ssub;
            boolean flag = true;
            for(int i=0; i<authorList.size()-1; i++){
                curr = authorList.get(i);
                sub = extract(curr);
                next = authorList.get(i+1);
                ssub = extract(next);
                if(!sub.equals(ssub)){
                    authorName.add(curr.substring(0,curr.length()-3));
                }
            }

        }

        private String extract(String s) {
            String sub = s.substring(s.length()-3,s.length());
            return sub;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setup();
        }
    }

    private void setup(){

        TableLayout tl = (TableLayout) findViewById(R.id.table);
        tl.setOrientation(LinearLayout.VERTICAL);

        ConstraintSet con = new ConstraintSet();


        for(i=1; i<authorName.size()-1; i++){
            LinearLayout row = new LinearLayout(this);
            final Button author_name = new Button(this);
            TextView book_name = new TextView(this);
            book_name.setText(String.format("%s\n", bookName.get(i)));
            author_name.setText(String.format("%s\n", authorName.get(i-1)));
            author_name.setLayoutParams(new LinearLayout.LayoutParams(300,100));
            author_name.setTextSize(6);
            row.addView(author_name);
            row.addView(book_name);
            tl.addView(row);
            final int index = i;
            author_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ScrollingActivity.this, DownloadActivity.class);
                    intent.putExtra("hyperL",link.get(index));
                    Log.d("TAG", link.get(index));
                    startActivity(intent);
                }
            });

        }

    }
}