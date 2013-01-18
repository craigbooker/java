package com.craigbooker.servicefinder;

import android.app.Fragment;

import com.craigbooker.servicefinder.FormFragment.FormListener;

public class FormFragment extends Fragment {

	private FormListener listener;
	
	public interface FormListener{
		public void onSearch(String searchTerm);
		public void onSearchLog();
		public void onAddToLog();
		
	}
}
