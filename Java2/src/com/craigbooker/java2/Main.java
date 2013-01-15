package com.craigbooker.java2;

import com.craigbooker.java2.R;
import com.craigbooker.java2.MainListFragment;
import com.craigbooker.java2.MainDetailActivity;
import com.craigbooker.java2.MainDetailFragment;


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
		setContentView(R.layout.activity_main_list);
		
		if (findViewById(R.id.main_detail_container) != null) {
			mTwoPane = true;
			
			// In two-pane mode, list items should be given the 'activated' state when touched.
			((MainListFragment) getSupportFragmentManager()
								.findFragmentById(R.id.main_list))
								.setActivateOnItemClick(true);
		}	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}


	/** Callback method from {@link MainListFragment.Callbacks} indicating that the item with the given ID was selected. */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
		// adding or replacing the detail fragment using a
		// fragment transaction.
		Bundle arguments = new Bundle();
		arguments.putString(MainDetailFragment.ARG_ITEM_ID, id);
		MainDetailFragment fragment = new MainDetailFragment();
		fragment.setArguments(arguments);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_detail_container, fragment)
				.commit();

	} else {
		// In single-pane mode, simply start the detail activity for the selected item ID.
		Intent detailIntent = new Intent(this, MainDetailActivity.class);
		detailIntent.putExtra(MainDetailFragment.ARG_ITEM_ID, id);
		startActivity(detailIntent);
		}
	}
	
}