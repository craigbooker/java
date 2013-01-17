package com.craigbooker.autoservicefinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

/**
 * This activity represents a single ServiceCategory detail screen.  On tablet-size devices, item
 * details are presented side-by-side with a list of items in a {@link ServiceCategoryListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link ServiceCategoryDetailFragment}.
 */
public class ServiceCategoryDetailActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_servicecategory_detail);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity using a fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(
					ServiceCategoryDetailFragment.ARG_ITEM_ID,
					getIntent().getStringExtra(
							ServiceCategoryDetailFragment.ARG_ITEM_ID));
			ServiceCategoryDetailFragment fragment = new ServiceCategoryDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.servicecategory_detail_container, fragment).commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
 
			NavUtils.navigateUpTo(this, new Intent(this,
					ServiceCategoryListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
