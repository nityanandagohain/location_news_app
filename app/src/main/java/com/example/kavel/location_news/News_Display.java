package com.example.kavel.location_news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class News_Display extends AppCompatActivity {

    String locality;
    String subAdminArea;
    String AdminArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__display);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        locality = bundle.getString("locality");
        subAdminArea = bundle.getString("subAdminArea");
        AdminArea = bundle.getString("AdminArea");
        ListView newsDisplay = (ListView)findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();
        newsDisplay.setAdapter(customAdapter);

    }

    class CustomAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return 10;
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
}
