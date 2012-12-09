package com.eatrightapp.android;

import java.util.HashMap;
import java.util.Map;

import com.eatrightapp.android.geolocation.Waldo;
import com.eatrightapp.android.lazylist.ImageLoader;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

public class EatRightApp extends Application {
	private Map<String, Map<String, Object>> activityStates = new HashMap<String, Map<String, Object>>();
	private Waldo waldo = Waldo.getInstance();
	private ImageLoader imageLoader;

	public Waldo getWaldo() {
		return waldo;
	}

	public Map<String, Map<String, Object>> getActivityStates() {
		return activityStates;
	}

	public ImageLoader getImageLoader() {
		return imageLoader;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		Log.d(getClass().getName(), "onConfigurationChanged()");
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		imageLoader = new ImageLoader(getApplicationContext());
		Log.d(getClass().getName(), "onCreate()");
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		Log.d(getClass().getName(), "onLowMemory()");
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		Log.d(getClass().getName(), "onTerminate()");
	}

}
