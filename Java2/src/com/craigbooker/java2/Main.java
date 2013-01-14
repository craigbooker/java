package com.craigbooker.java2;

import com.craigbooker.j2project1masterdetail.R;
import com.craigbooker.j2project1masterdetail.ServiceTypeListFragment;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

public class Main extends FragmentActivity implements MainListFragment.Callbacks {
	
	private boolean mTwoPane;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (findViewById(R.id.servicetype_detail_container) != null) {
			mTwoPane = true;
			
			// In two-pane mode, list items should be given the 'activated' state when touched.
			((MainListFragment) getSupportFragmentManager()
								.findFragmentById(R.id.servicetype_list))
								.setActivateOnItemClick(true);
		}	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
