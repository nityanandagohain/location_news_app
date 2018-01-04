package com.example.kavel.location_news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CustomSearchActivity extends AppCompatActivity {

    Spinner filterSpinner;
    Spinner sortBySpinner;
    EditText keywordEditor;
    int uniqueId = 2;
    String[] filters = new String[] {"top-headlines", "everything"};
    String[] sortBy = new String[] {"publishedAt", "relevancy", "popularity"};
    String selectedFilter = null;
    String SelectedSortItem = null;
    String keyword = null;

    public void onGoSelected(View view){

        keyword = keywordEditor.getText().toString();
        if(keyword.matches("")){

            Toast.makeText(this, "Please enter keyword. ", Toast.LENGTH_SHORT).show();
        }
        else {

            Intent intent = new Intent(getApplicationContext(), News_Display.class);
            Bundle bundle = new Bundle();
            bundle.putString("keyword", keyword);
            bundle.putString("selectedFilter", selectedFilter);
            bundle.putString("SelectedSortItem", SelectedSortItem);
            bundle.putInt("uniqueId", uniqueId);
            intent.putExtras(bundle);
            startActivity(intent);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_search);

        filterSpinner = findViewById(R.id.spinnerFilter);
        sortBySpinner = findViewById(R.id.spinnersortby);
        keywordEditor = findViewById(R.id.editText);

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selectedFilter = filters[i];


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sortBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                SelectedSortItem = sortBy[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
