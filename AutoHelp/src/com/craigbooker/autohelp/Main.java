package com.craigbooker.autohelp;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends MapActivity {

	private MapController mapController;
	private MapView mapView;
	private LocationManager locationManager;
	private GeoPoint currentPoint;
	private Location currentLocation = null;
	private AHelpOverlay currentPos;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);
		mapController = mapView.getController();
		mapController.setZoom(13);
		getLastLocation();
		drawCurrPositionOverlay;
		drawAutoHelp();
		animateToCurrentLocation();
	}
	
	public void getLastLocation() {
		String provider = getBestProvider();
		currentLocation = locationManager.getLastKnownLocation(provider);
		
		currentPoint = new GeoPoint(632899,3927508);
    	currentLocation = new Location("");
    	currentLocation.setLatitude(currentPoint.getLatitudeE6() / 1e6);
    	currentLocation.setLongitude(currentPoint.getLongitudeE6() / 1e6);
		
		if(currentLocation != null){
    		setCurrentLocation(currentLocation);
    	}
    	else
    	{
    		Toast.makeText(this, "Location not yet acquired", Toast.LENGTH_LONG).show();
    	}
    	((TextView)findViewById(R.id.providerText)).setText("Provider :" + getBestProvider());
		
	}
	
	public void animateToCurrentLocation(){
		if(currentPoint!=null){
			mapController.animateTo(currentPoint);
		}
	}
	
	public void centerToCurrentLocation(View view){
		animateToCurrentLocation();
	}

	public String getBestProvider(){
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
		criteria.setAccuracy(Criteria.NO_REQUIREMENT);
		String bestProvider = locationManager.getBestProvider(criteria, true);
		return bestProvider;
	}
	
public void setCurrentLocation(Location location){
	int currLatitude = (int) (location.getLatitude()*1E6); //14S
	int currLongitude = (int) (location.getLongitude()*1E6);
	currentPoint = new GeoPoint(currLatitude, currLongitude);
	
	currentLocation = new Location("");
	currentLocation.setLatitude(currentPoint.getLatitudeE6() / 1e6);
	currentLocation.setLongitude(currentPoint.getLongitude() / 1e6);
	
	((TextView)findViewById(R.id.latitudeText))setText("Latitude: " + String.valueOf((int)(currentLocation.getLatitude()*1E6)));
	((TextView)findViewById(R.id.longitudeText))setText("Longitude: " + String.valueOf((int)(currentLocation.getLongitude()*1E6)));
	((TextView)findViewById(R.id.accuracyText))setText("Accuracy: " + String.valueOf((int)(currentLocation.getAccuracy() + " m")));
	drawCurrPositionOverlay();
}	

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
