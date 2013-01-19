package com.craigbooker.servicefinder;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.craigbooker.servicefinder.FormFragment.FormListener;

public class FormFragment extends Fragment {

	private FormListener listener;
	
	public interface FormListener{
		public void onSearch(String searchTerm);
		public void onSearchLog();
		public void onAddToLog();
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.form, container, false);
		
		Button searchButton = (Button) view.findViewById(R.id.searchTermField);
		
		
		return view;
	}
	
}  //END OF PUBLIC CLASS FORMFRAGMENT
