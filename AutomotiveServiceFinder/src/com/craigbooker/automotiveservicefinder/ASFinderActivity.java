package com.craigbooker.automotiveservicefinder;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.craigbooker.automotiveservicefinder.ASOverlay;

public class ASFinderActivity extends MapActivity {

	private MapController mapController;
	private MapView mapView;
	private LocationManager locationManager;
	private GeoPoint currentPoint;
	private Location currentLocation = null;
	private MallOverlay currPos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		

		
		mapView = (MapView)findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);
		//mapView.setStreetView(true);
		mapController = mapView.getController();
		mapController.setZoom(13);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_asfinder, menu);
		return true;
	}
	
	@Override
	protected boolean isRouteDisplayed() {
	    // TODO Auto-generated method stub
	    return false;
	}
}
