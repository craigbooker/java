package com.craigbooker.autoservicefinder;

import com.craigbooker.lib.FileStuff;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class FormFragment extends Fragment {

	private FormListener listener;
	
	public interface FormListener{
		public void onSearchTerm(String term);
		public void onSearchTermList();
		public void onAddSearchTerm();
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.form, container, false);
		
		//ADD SEARCH HANDLER
		Button searchButton = (Button) view.findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText field = (EditText) getActivity().findViewById(R.id.searchTermField);
				String searchTerm = field.getText().toString();
				field.setText(searchTerm);
				InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(field.getWindowToken(), 0);
				listener.onSearchTerm(searchTerm);
			}
		});
		
		//GO TO HISTORY BUTTON
		Button goHistoryButton = (Button) view.findViewById(R.id.historyButton);
		goHistoryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onSearchTermList();
			}
		});
		
		// ADD HISTORY BUTTON
		Button addHistoryButton = (Button) view.findViewById(R.id.addHistoryButton);
		addHistoryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v){
				listener.onAddSearchTerm();

			}
		});
		
		return view;
	};
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		try{
			listener = (FormListener) activity;
		} catch (ClassCastException e){
			throw new ClassCastException(activity.toString() + " must implement FormListener");
		}
	}
}
