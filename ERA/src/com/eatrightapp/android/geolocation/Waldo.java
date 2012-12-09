package com.eatrightapp.android.geolocation;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class Waldo implements LocationListener {

	private static final int MINUTES = 1000 * 60;
	private static final int MIN_DISTANCE_IN_METERS = 500;
	private Context context;
	private boolean running = false;
	private LocationManager locationManager;
	private Location location;
	
	private Waldo() {
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void start(Context context) {
		if(!running) {
			this.context = context;
			this.running = true;
	
			// Acquire a reference to the system Location Manager
			locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
	
			// Register the listener with the Location Manager to receive location
			// updates
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 3*MINUTES, MIN_DISTANCE_IN_METERS, this);
	
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 3*MINUTES, MIN_DISTANCE_IN_METERS, this);
			
			// Try to find out last known location and use that as a starting value.	
			Location lastKnownGPSLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if(isBetterLocation(lastKnownGPSLocation, location)) {
				location = lastKnownGPSLocation;
			}
			
			Location lastKnownNetworkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if(isBetterLocation(lastKnownNetworkLocation, location)) {
				location = lastKnownNetworkLocation;
			}
		}
	}

	public void stop() {
		if(running) {
			locationManager.removeUpdates(this);
			running = false;
		}
	}
	
	public void resume() {
		start(context);
	}
	
	private static class SingletonHolder {
		public static final Waldo INSTANCE = new Waldo();
	}

	public static Waldo getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * Determines whether one Location reading is better than the current
	 * Location fix
	 * 
	 * @param location
	 *            The new Location that you want to evaluate
	 * @param currentBestLocation
	 *            The current Location fix, to which you want to compare the new
	 *            one
	 */
	private boolean isBetterLocation(Location location,
			Location currentBestLocation) {
		if(location == null) {
			return false;
		}
		
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > 2*MINUTES;
		boolean isSignificantlyOlder = timeDelta < -2*MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return true;
		}
		return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	@Override
	public void onLocationChanged(Location newLocation) {
		if(isBetterLocation(newLocation, location)) {
			location = newLocation;
		}

	}

	@Override
	public void onProviderDisabled(String provider) {
		stop();

	}

	@Override
	public void onProviderEnabled(String provider) {
		resume();

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}
}
