package com.craigbooker.autoservicefinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class ServiceCategoryListActivity extends FragmentActivity implements
		ServiceCategoryListFragment.Callbacks {

	/* Whether or not the activity is in two-pane mode, i.e. running on a tablet device. */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_servicecategory_list);

		if (findViewById(R.id.servicecategory_detail_container) != null) {

			mTwoPane = true;

			// In two-pane mode, list items should be given the 'activated' state when touched.
			((ServiceCategoryListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.servicecategory_list))
					.setActivateOnItemClick(true);
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	/* Callback method from {@link ServiceCategoryListFragment.Callbacks} indicating that the item with the given ID was selected. */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by adding or replacing the detail fragment using a fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(ServiceCategoryDetailFragment.ARG_ITEM_ID, id);
			ServiceCategoryDetailFragment fragment = new ServiceCategoryDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.servicecategory_detail_container, fragment)
					.commit();

		} else {
			// In single-pane mode, simply start the detail activity for the selected item ID.
			Intent detailIntent = new Intent(this, ServiceCategoryDetailActivity.class);
			detailIntent.putExtra(ServiceCategoryDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
}
