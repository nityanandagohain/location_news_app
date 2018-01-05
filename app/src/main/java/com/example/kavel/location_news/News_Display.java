package com.example.kavel.location_news;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    String subAdminArea = null;
    String AdminArea = null;
    ListView newsDisplay;
    ArrayList<String> titles = new ArrayList();
    private ProgressBar progressbar;
    private ListView listview;
    Intent intent;
    Bundle bundle;
    int uniqueId;
    ArrayList<String> author = new ArrayList();
    ArrayList<String> urlofNewsSource = new ArrayList();
    ArrayList<String> urlofImageSource = new ArrayList();
    ArrayList<String> description = new ArrayList();
    int count = 0;

    public String getSearchWord(){



        subAdminArea = bundle.getString("subAdminArea");
        AdminArea = bundle.getString("AdminArea");

         if(subAdminArea != null){

            return  subAdminArea;
        }

        return AdminArea;
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__display);

        if (haveNetworkConnection()) {
            intent = getIntent();
            bundle = intent.getExtras();
            uniqueId = bundle.getInt("uniqueId");
            Log.i("info", Integer.toString(uniqueId));
            DownloadTask task = new DownloadTask();
            if (uniqueId == 1) {

                Log.i("info", getSearchWord());
                task.execute("https://newsapi.org/v2/everything?q=+" + getSearchWord() + "&sortBy=popularity&language=en&apiKey=0a3f06e922a84e07b9b99fcbca201d58");
            } else if (uniqueId == 2) {

                Log.i("info", bundle.getString("selectedFilter"));
                Log.i("info", bundle.getString("keyword"));
                Log.i("info", bundle.getString("SelectedSortItem"));
                task.execute("https://newsapi.org/v2/" + bundle.getString("selectedFilter") + "?q=+" + bundle.getString("keyword") + "&sortBy= " + bundle.getString("SelectedSortItem") + "&language=en&apiKey=0a3f06e922a84e07b9b99fcbca201d58");

            }
            newsDisplay = findViewById(R.id.listView);
            progressbar = findViewById(R.id.progressBar2);
            listview = findViewById(R.id.listView);

            if (!titles.isEmpty()) {
                progressbar.setVisibility(View.INVISIBLE);
                listview.setVisibility(View.VISIBLE);
            }

            newsDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlofNewsSource.get(position)));
                    startActivity(browserIntent);
                }
            });
        }
        else {

            new AlertDialog.Builder(this)
                    .setTitle("Connection Error. ")
                    .setMessage("Please enable your internet connection. ")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    })
                    .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .show();
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
            if(urlofImageSource.get(i).equals("null")) {

                Picasso.with(News_Display.this).load(R.drawable.no_image_available).fit().centerCrop().into(newsImage);
            }
            else{

                Picasso.with(News_Display.this).load(urlofImageSource.get(i)).fit().centerCrop().into(newsImage);


            }
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
                    description.add((jsonPart.getString("description")));


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
