package com.example.kavel.location_news;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

    String locality;
    String subAdminArea;
    String AdminArea;
    ArrayList<String> titles = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__display);

        DownloadTask task = new DownloadTask();
        task.execute("https://newsapi.org/v2/everything?q=jorhat&apiKey=0a3f06e922a84e07b9b99fcbca201d58");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        locality = bundle.getString("locality");
        subAdminArea = bundle.getString("subAdminArea");
        AdminArea = bundle.getString("AdminArea");
        ListView newsDisplay = findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();
        newsDisplay.setAdapter(customAdapter);

    }

    class CustomAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return 100;
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

            ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
            TextView textView = (TextView)view.findViewById(R.id.textView);
            TextView textView2 = (TextView)view.findViewById(R.id.textView2);

            imageView.setBackgroundResource(R.drawable.icon1);
            textView.setText(locality);
            textView2.setText(AdminArea);
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

                while (data != -1){

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
                for(int i = 0; i < arr.length(); i++){

                    JSONObject jsonPart = arr.getJSONObject(i);
                    titles.add(jsonPart.getString("title"));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("title5", titles.get(5));

        }
    }
}
