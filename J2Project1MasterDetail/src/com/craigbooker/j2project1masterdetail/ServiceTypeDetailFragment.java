package com.craigbooker.j2project1masterdetail;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.craigbooker.j2project1masterdetail.dummy.DummyContent;

/**
 * A fragment representing a single ServiceType detail screen. This fragment is
 * either contained in a {@link ServiceTypeListActivity} in two-pane mode (on
 * tablets) or a {@link ServiceTypeDetailActivity} on handsets.
 */
public class ServiceTypeDetailFragment extends Fragment {
	protected String[] mBusinessNames;
	protected String[] mBusinessPhone;
	
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
	public ServiceTypeDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Resources resources = getResources();
		//mBusinessNames = resources.getStringArray(R.array.businessNames);
		mBusinessPhone = resources.getStringArray(R.array.businessNumbers);
		
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mBusinessPhone);
		//setListAdapter(adapter);
		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_servicetype_detail, container, false);

		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			((TextView) rootView.findViewById(R.id.servicetype_detail)).setText(mItem.description);
		}

		return rootView;
	}
}
