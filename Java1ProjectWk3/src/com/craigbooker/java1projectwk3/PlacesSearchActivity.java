package com.craigbooker.java1projectwk3;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;


import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.craigbooker.external.yelp.v2.Business;
import com.craigbooker.external.yelp.v2.YelpError;
import com.craigbooker.external.yelp.v2.YelpSearchResult;
import com.craigbooker.external.yelp.v2.YelpService;

public class PlacesSearchActivity extends Activity{
    private ListView placesListView;
    private Button locationBtn;
    private Button searchBtn;
    private List<Business> placesRowData;
    private EditText queryText;
    private DecimalFormat distanceFormat = new DecimalFormat("#,##0.0"); // format
    
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // setContentView(R.layout.main);
            setContentView(R.layout.places);

            LinearLayout emptyResultsContainer = (LinearLayout) findViewById(R.id.placesListNoResultsLayout);

            placesListView = (ListView) findViewById(R.id.placesListView);
            placesListView.setEmptyView(emptyResultsContainer);
            placesListView.setOnItemClickListener(new OnItemClickListener() {
            	public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            		
            		
            	}
                
		    });
	}
}
