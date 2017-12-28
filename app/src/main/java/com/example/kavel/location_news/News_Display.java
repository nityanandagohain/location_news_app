package com.example.kavel.location_news;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class News_Display extends AppCompatActivity {

    String subAdminArea;
    String AdminArea;
    ListView newsDisplay;
    ArrayList<String> titles = new ArrayList();
    private ProgressBar progressbar;
    private ListView listview;
    ArrayList<String> author = new ArrayList();
    ArrayList<String> urlofNewsSource = new ArrayList();
    ArrayList<String> urlofImageSource = new ArrayList();
    int count = 0;

    public String getSearchWord(){


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        subAdminArea = bundle.getString("subAdminArea");
        AdminArea = bundle.getString("AdminArea");

         if(subAdminArea != null){

            return  subAdminArea;
        }

        return AdminArea;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__display);

        DownloadTask task = new DownloadTask();
        String searchWord = getSearchWord();
        Log.i("searchWord", searchWord);
        task.execute("https://newsapi.org/v2/everything?q=+" + searchWord + "&sortBy=popularity&language=en&apiKey=0a3f06e922a84e07b9b99fcbca201d58");
        newsDisplay = findViewById(R.id.listView);
        progressbar = findViewById(R.id.progressBar2);
        listview = findViewById(R.id.listView);


        if(!titles.isEmpty()){
            progressbar.setVisibility(View.INVISIBLE);
            listview.setVisibility(View.VISIBLE);
        }




    }

    public class CustomAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = getLayoutInflater().inflate(R.layout.customlayout, null);
            ImageView newsImage = view.findViewById(R.id.imageView);
            TextView headline = view.findViewById(R.id.headlineView);
            TextView authorView =view.findViewById(R.id.authorView);
            Picasso.with(News_Display.this).load(urlofImageSource.get(i)).into(newsImage);

            if(titles.get(i) != null) {

                headline.setText(titles.get(i));
            }
            else{

                Log.i("title", "N/A");

            }

            if(author.get(i) != null) {
                if(author.get(i).equals("null")){
                    authorView.setText("N/A");
                }
                else {
                    authorView.setText(author.get(i));
                }
            }
            else
            {
                Log.i("title", "N/A");
            }
            return view;
        }
    }
    public class DownloadTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject newsJSON = new JSONObject(s);
                String articles = newsJSON.getString("articles");
                JSONArray arr = new JSONArray(articles);
                for (int i = 0; i < arr.length(); i++) {
                    if (i == 0) {
                        progressbar.setVisibility(View.INVISIBLE);
                        listview.setVisibility(View.VISIBLE);
                    }

                    count = i;
                    JSONObject jsonPart = arr.getJSONObject(i);
                    titles.add(jsonPart.getString("title"));
                    author.add(jsonPart.getString("author"));
                    urlofNewsSource.add(jsonPart.getString("url"));
                    urlofImageSource.add(jsonPart.getString("urlToImage"));

                }

                CustomAdapter customAdapter = new CustomAdapter();
                newsDisplay.setAdapter(customAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("Error occured", "Error");
            }


        }
    }

}
