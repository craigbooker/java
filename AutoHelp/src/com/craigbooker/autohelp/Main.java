package com.craigbooker.autohelp;

import java.util.List;

import com.craigbooker.autohelp.AHelpOverlay;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends MapActivity implements LocationListener {

	private MapController mapController;
	private MapView mapView;
	private LocationManager locationManager;
	private GeoPoint currentPoint;
	private Location currentLocation = null;
	private AHelpOverlay currentPos;
	private AHelpOverlay currPos;	
	
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
		drawCurrPositionOverlay();
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
		currentLocation.setLongitude(currentPoint.getLongitudeE6() / 1e6);
		
		((TextView)findViewById(R.id.latitudeText)).setText("Latitude: " + String.valueOf((int)(currentLocation.getLatitude()*1E6)));
		((TextView)findViewById(R.id.longitudeText)).setText("Longitude: " + String.valueOf((int)(currentLocation.getLongitude()*1E6)));
		((TextView)findViewById(R.id.accuracyText)).setText("Accuracy: " + String.valueOf(location.getAccuracy() + " m"));
		drawCurrPositionOverlay();
	}	
	public void drawCurrPositionOverlay(){
		List<Overlay> overlays = mapView.getOverlays();
		overlays.remove(currPos);
		Drawable marker = getResources().getDrawable(R.drawable.me);
		currPos = new AHelpOverlay(marker,mapView);
		if(currentPoint!=null){
			OverlayItem overlayitem = new OverlayItem(currentPoint, "Me", "Here I am!");
			currPos.addOverlay(overlayitem);
			overlays.add(currPos);
			currPos.setCurrentLocation(currentLocation);
		}
	}
	
	   public void drawAutoHelp(){
	    	Drawable marker = getResources().getDrawable(R.drawable.business);
	    	AHelpOverlay autoHelpPos = new AHelpOverlay(marker,mapView);
	    	GeoPoint[] autoHelpCoords = new GeoPoint[6];
	    	
	    	//Load Some Random Coordinates in Miami, FL
	    	autoHelpCoords[0] = new GeoPoint(29656582,-82411151);//AH Business 0
	    	autoHelpCoords[1] = new GeoPoint(29649831,-82376347);//AH Business 1
	    	autoHelpCoords[2] = new GeoPoint(29674146,-8238905);//AH Business 2
	    	autoHelpCoords[3] = new GeoPoint(29675078,-82322617);//AH Business 3
	    	autoHelpCoords[4] = new GeoPoint(29677017,-82339761);//AH Business 4
	    	autoHelpCoords[5] = new GeoPoint(29663835,-82325599);//AH Business 5   	
	   	
	    	
	    	List<Overlay> overlays = mapView.getOverlays();
			OverlayItem overlayItem = new OverlayItem(autoHelpCoords[0], "AH Business 0", "AH Address City, ST zipcode");
			autoHelpPos.addOverlay(overlayItem);
			overlayItem = new OverlayItem(autoHelpCoords[1], "AH Business 1", "AH Address City, ST zipcode");
			autoHelpPos.addOverlay(overlayItem);
			overlayItem = new OverlayItem(autoHelpCoords[2], "AH Business 2", "AH Address City, ST zipcode");
			autoHelpPos.addOverlay(overlayItem);
			overlayItem = new OverlayItem(autoHelpCoords[3], "AH Business 3", "AH Address City, ST zipcode");
			autoHelpPos.addOverlay(overlayItem);
			overlayItem = new OverlayItem(autoHelpCoords[4], "AH Business 4", "AH Address City, ST zipcode");
			autoHelpPos.addOverlay(overlayItem);
			overlayItem = new OverlayItem(autoHelpCoords[5], "AH Business 5", "AH Address City, ST zipcode");
			autoHelpPos.addOverlay(overlayItem);
			overlays.add(autoHelpPos);	
			
			autoHelpPos.setCurrentLocation(currentLocation);
	    }
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onLocationChanged(Location newLocation) {
		setCurrentLocation(newLocation);
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(getBestProvider(), 1000, 1, this);
	}


	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}



	@Override
	public void onProviderDisabled(String arg0) {
		Toast.makeText(this, "Provider Disabled", Toast.LENGTH_SHORT).show();
		
	}


	@Override
	public void onProviderEnabled(String arg0) {
		Toast.makeText(this, "Provider Enabled", Toast.LENGTH_SHORT).show();
		
	}


	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		Toast.makeText(this, "Staus Changed", Toast.LENGTH_SHORT).show();
		
	}
	
}
