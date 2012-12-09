package com.eatrightapp.android;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.eatrightapp.android.lazylist.ImageLoader;
import com.eatrightapp.external.yelp.v2.Business;
import com.eatrightapp.external.yelp.v2.YelpError;
import com.eatrightapp.external.yelp.v2.YelpSearchResult;
import com.eatrightapp.external.yelp.v2.YelpService;
import com.eatrightapp.util.Convert;

public class PlacesSearchActivity extends Activity {
	private EatRightApp app;
	private ListView placesListView;
	private Button locationBtn;
	private Button searchBtn;
	private List<Business> placesRowData;
	private EditText queryText;
	private DecimalFormat distanceFormat = new DecimalFormat("#,##0.0"); // format
																			// //
																			// place
	private ImageLoader imageLoader;
	private String myLocation = null;

	public Map<String, Object> getActivityState() {
		Map<String, Object> state = new HashMap<String, Object>();
		state.put("placesRowData", placesRowData);
		state.put("queryText", queryText.getText());
		state.put("myLocation", myLocation);
		return state;
	}

	public void setActivityState(Map<String, Object> state) {
		if (state == null) {
			return;
		}
		placesRowData = (List<Business>) state.get("placesRowData");
		queryText.setText((CharSequence) state.get("queryText"));
		myLocation = (String) state.get("myLocation");
	}
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// activity = this;
		app = (EatRightApp) getApplication();
		app.getWaldo().start(getApplicationContext());
		imageLoader = app.getImageLoader();
 
		// setContentView(R.layout.main);
		setContentView(R.layout.places);

		LinearLayout emptyResultsContainer = (LinearLayout) findViewById(R.id.placesListNoResultsLayout);

		placesListView = (ListView) findViewById(R.id.placesListView);
		placesListView.setEmptyView(emptyResultsContainer);
		placesListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d(getClass().getName(), "Starting RestaurantActivity");
				Intent showRestaurant = new Intent();
				showRestaurant.setClassName("com.eatrightapp.android", "com.eatrightapp.android.RestaurantActivity");
				showRestaurant.putExtra("com.eatrightapp.android.PlacesSearchActivity.YelpId", placesRowData.get(position).getId());
				showRestaurant.putExtra("com.eatrightapp.android.PlacesSearchActivity.EatRightAppId",
						buildEatRightAppId(placesRowData.get(position).getId(), placesRowData.get(position).getLocation().getCity()));
				startActivity(showRestaurant);
			}

			private String buildEatRightAppId(String id, String city) {
				return id.replaceAll("-" + city.toLowerCase().replaceAll("[^a-z0-9]", "-") + "$", "");
				// return city.toLowerCase().replaceAll("[^a-z0-9].", "-");
			}
		});

		queryText = (EditText) findViewById(R.id.query);
		queryText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					searchBtn.performClick();
					return true;
				}
				return false;
			}

		});

		searchBtn = (Button) findViewById(R.id.SearchBtn);
		searchBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(queryText.getWindowToken(), 0);
				search();
			}
		});

		locationBtn = (Button) findViewById(R.id.LocationButton);
		locationBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				promptForLocation();
			}
		});

		// Restore app state if possible.
		if (app.getActivityStates().containsKey(getClass().getCanonicalName())) {
			setActivityState(app.getActivityStates().get(getClass().getCanonicalName()));
			updatePlacesRowData(placesRowData);
			redrawPlacesList();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		app.getActivityStates().put(getClass().getCanonicalName(), getActivityState());
	}

	private class YelpQuery {
		public Location location;
		public String term;
		public String categoryFilter;
	}

	private class YelpSearchTask extends AsyncTask<YelpQuery, Integer, Long> {
		private ProgressDialog progressDialog;
		private YelpQuery yelpQuery;
		YelpSearchResult restaurants;

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			progressDialog = new ProgressDialog(PlacesSearchActivity.this);
			// progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			// // For doing a true progress bar.
			progressDialog.setMessage("Loading. Please wait...");
			progressDialog.setCancelable(true);
			progressDialog.show();

		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			// progressDialog.setProgress(values[0]); // For doing a true
			// progress bar.
			// progressDialog.setMax(values[1]); // For doing a true progress
			// bar.
		}

		@Override
		protected Long doInBackground(YelpQuery... params) {
			yelpQuery = params[0];

			int radius = YelpService.SEARCH_RADIUS;
			int limit = 20;
			String sort = "1";

			Double lat = yelpQuery.location == null ? null : yelpQuery.location.getLatitude();
			Double lng = yelpQuery.location == null ? null : yelpQuery.location.getLongitude();

			YelpError error = null;

			restaurants = YelpService.search(lat, lng, myLocation, yelpQuery.term, yelpQuery.categoryFilter, radius, limit, 0, sort);
			if (restaurants != null) {
				if (restaurants.getError() != null) {
					error = restaurants.getError();
				}
				//while (error == null && restaurants.getBusinesses().size() < restaurants.getTotal()) {
				// ** This was exhasuting my test api limits, so changed to only pull in first batch
				// of results.  If you want to go back to getting more, change the if(error...) below to
				// the commented out while(error... above!
				if(error == null) {
					YelpSearchResult morePlaces = YelpService.search(lat, lng, myLocation, yelpQuery.term, yelpQuery.categoryFilter, radius, limit, restaurants
							.getBusinesses().size(), sort);
					if (morePlaces.getError() != null) {
						error = morePlaces.getError();
					} else {
						restaurants.getBusinesses().addAll(morePlaces.getBusinesses());
						// publishProgress(restaurants.getBusinesses().size(),
						// restaurants.getTotal()); // For doing a true progress
						// bar.
					}
				}
			} else {
				// TODO: Show error? No results from yelp! Probably no network connectivity.
				
			}

			return null;
		}

		@Override
		protected void onPostExecute(Long result) {
			super.onPostExecute(result);
			progressDialog.dismiss();
			if(restaurants == null) {
				Toast.makeText(getApplicationContext(), "No results, maybe the network is down?", Toast.LENGTH_LONG).show();
			} else {
				if (restaurants.getError() != null) {
					YelpError e = restaurants.getError();
					AlertDialog.Builder builder = new AlertDialog.Builder(PlacesSearchActivity.this);
					StringBuilder sb = new StringBuilder();
					sb.append("The search service has encountered an error.\n");
					if (e.getId() != null) {
						sb.append(e.getId()).append("\n");
					}
					if (e.getField() != null) {
						sb.append(e.getField()).append("\n");
					}
					if (e.getText() != null) {
						sb.append(e.getText()).append("\n");
					}
					if (e.getDescription() != null) {
						sb.append(e.getDescription()).append("\n");
					}
					sb.append("Do you want to quit?");
					builder.setMessage(sb).setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							PlacesSearchActivity.this.finish();
						}
					}).setNegativeButton("No", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
					builder.show();
				} else {
					updatePlacesRowData(restaurants.getBusinesses());
					redrawPlacesList();
				}
			}
		}
	}

	private void promptForLocation() {
		final AlertDialog.Builder locationDialog = new AlertDialog.Builder(this);

		locationDialog.setTitle("Search near");
		locationDialog.setMessage("Enter a City, State or Postal Code: ");

		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		locationDialog.setView(input);

		locationDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				myLocation = input.getText().toString();
				if (myLocation != null && myLocation.trim().length() == 0) {
					myLocation = null;
				}
				locationBtn.setText("Near " + (myLocation == null ? "me" : myLocation));
				dialog.dismiss();
			}
		});

		locationDialog.setNegativeButton("My Location", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				myLocation = null;
				locationBtn.setText("Near " + (myLocation == null ? "me" : myLocation));
				dialog.dismiss();
			}
		});

		locationDialog.show();

	}

	private void search() {
		locationBtn.setText("Near " + (myLocation == null ? "me" : myLocation));
		Location location = myLocation == null ? app.getWaldo().getLocation() : null;

		if (app.getWaldo().getLocation() == null && (myLocation == null || myLocation.trim().length() == 0)) {
			Log.d("Nick: onPreExecute()", "Show location error");
			AlertDialog.Builder builder = new AlertDialog.Builder(PlacesSearchActivity.this);
			builder.setMessage("Unable to determine your location. Please ensure a 'My Location Source' is enabled in System Settings - Security and Location.")
					.setCancelable(false).setPositiveButton("Okay", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			AlertDialog alert = builder.create();
			alert.show();
		} else {
			YelpQuery query = new YelpQuery();
			query.location = location;
			query.categoryFilter = null;
			String queryString = queryText.getText().toString();
			if (queryString == null || queryString.trim().equalsIgnoreCase("")) {
				query.term = "restaurants";
				query.categoryFilter = null;
			} else {
				query.term = queryString;
				query.categoryFilter = "restaurants";
			}
			new YelpSearchTask().execute(query);
		}

	}

	private void updatePlacesRowData(List<Business> list) {
		this.placesRowData = list;
	}

	private void redrawPlacesList() {
		if (placesRowData != null && placesRowData.size() > 0) {
			placesListView.setAdapter(new BusinessAdapter(this, R.layout.places_list_row, placesRowData));
		}
	}

	private class BusinessAdapter extends ArrayAdapter<Business> {

		private List<Business> businesses;

		public BusinessAdapter(Context context, int textViewResourceId, List<Business> objects) {
			super(context, textViewResourceId, objects);
			businesses = objects;
		}

		private class BusinessViewHolder {
			public TextView businessNameTV;
			public TextView addressTV;
			public TextView distanceTV;
			public ImageView businessIV;
			public ImageView ratingIV;
			public TextView reviewsQtyTV;
			public TextView snippetTextTV;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			BusinessViewHolder cachedView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.places_list_row, null);
				cachedView = new BusinessViewHolder();
				cachedView.businessNameTV = (TextView) v.findViewById(R.id.BusinessName);
				cachedView.addressTV = (TextView) v.findViewById(R.id.Address);
				cachedView.distanceTV = (TextView) v.findViewById(R.id.Distance);
				cachedView.businessIV = (ImageView) v.findViewById(R.id.Image);
				cachedView.ratingIV = (ImageView) v.findViewById(R.id.RatingImage);
				cachedView.reviewsQtyTV = (TextView) v.findViewById(R.id.ReviewsQty);
				cachedView.snippetTextTV = (TextView) v.findViewById(R.id.SnippetText);
				v.setTag(cachedView);
			} else {
				cachedView = (BusinessViewHolder) v.getTag();
			}
			Business biz = businesses.get(position);
			StringBuilder address = new StringBuilder();
			if (biz.getLocation() != null && biz.getLocation().getDisplayAddress() != null) {
				for (String addrLine : biz.getLocation().getDisplayAddress()) {
					address.append(addrLine).append("\n");
				}
			}

			if (cachedView.businessNameTV != null) {
				cachedView.businessNameTV.setText(biz.getName());
			}
			if (cachedView.addressTV != null) {
				cachedView.addressTV.setText(address);
			}
			if (cachedView.distanceTV != null) {
				cachedView.distanceTV.setText(distanceFormat.format(Convert.metersToMiles(biz.getDistance())) + " mi");
			}
			if (cachedView.businessIV != null) {
				if (biz.getImageUrl() != null) {
					cachedView.businessIV.setTag(biz.getImageUrl().toExternalForm());
					imageLoader.DisplayImage(biz.getImageUrl().toExternalForm(), PlacesSearchActivity.this, cachedView.businessIV);
				} else {
					cachedView.businessIV.setImageBitmap(null);
				}

			}
			if (cachedView.ratingIV != null) {
				if (biz.getRatingImgUrl() != null) {
					cachedView.ratingIV.setTag(biz.getRatingImgUrl().toExternalForm());
					imageLoader.DisplayImage(biz.getRatingImgUrl().toExternalForm(), PlacesSearchActivity.this, cachedView.ratingIV);
				} else {
					cachedView.ratingIV.setImageBitmap(null);
				}
			}
			if (cachedView.reviewsQtyTV != null) {
				cachedView.reviewsQtyTV.setText(biz.getReviewCount() + " Reviews");
			}
			if (cachedView.snippetTextTV != null) {
				cachedView.snippetTextTV.setText(biz.getSnippetText());
			}

			return v;
		}

	}

}
