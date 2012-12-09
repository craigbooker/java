package com.craigbooker.java1projectwk3;

import com.craigbooker.external.yelp.v2.Business;
import com.craigbooker.external.yelp.v2.YelpError;
import com.craigbooker.external.yelp.v2.YelpSearchResult;
import com.craigbooker.external.yelp.v2.YelpService;

public class PlacesSearchActivity extends Activity{
    private EatRightApp app;
    private ListView placesListView;
    private Button locationBtn;
    private Button searchBtn;
    private List<Business> placesRowData;
    private EditText queryText;
    private DecimalFormat distanceFormat = new DecimalFormat("#,##0.0"); // format
}
