package com.craigbooker.autoservicefinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.craigbooker.autoservicefinder.YelpFragment;

import com.craigbooker.autoservicefinder.dummy.DummyContent;

/**
 * A fragment representing a single ServiceCategory detail screen. This fragment
 * is either contained in a {@link ServiceCategoryListActivity} in two-pane mode
 * (on tablets) or a {@link ServiceCategoryDetailActivity} on handsets.
 */
public class ServiceCategoryDetailFragment extends ListFragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private DummyContent.DummyItem mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ServiceCategoryDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment arguments. In a real-world scenario, use a Loader to load content from a content provider.
			mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
			String argString = mItem.toString();
			Log.e("TEST OUTPUT:", argString);
			
			// In single-pane mode, simply start the detail activity for the selected item ID.
						Intent yelpIntent = new Intent(this, YelpFragment.class);
						yelpIntent.putExtra(ServiceCategoryDetailFragment.ARG_ITEM_ID, argString);
						startActivity(yelpIntent);
			
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_servicecategory_detail, container, false);

		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			((TextView) rootView.findViewById(R.id.servicecategory_detail))
					.setText(mItem.content);
		}

		return rootView;
	}
}
