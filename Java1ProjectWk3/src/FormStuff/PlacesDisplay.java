package FormStuff;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlacesDisplay extends LinearLayout {

	TextView _bizName;
	TextView _address;
	TextView _city;
	TextView _bizURL;
	LayoutParams lp;
	
	String pBizName = "";
	String pAddress = "";
	String pCity = "";
	String pBizURL = "";
	
	
	
	Context _context;
	
	public PlacesDisplay(Context context) {
		super(context);
		
		this.setOrientation(LinearLayout.VERTICAL);
		lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		this.setLayoutParams(lp);
		_context = context;
		
		_bizName = new TextView(_context);
		_bizName.setId(30);
		_bizName.setText("Business:" + pBizName);
		

		_address = new TextView(_context);
		_address.setId(31);
		_address.setText("Address:" + pAddress);		

		_city = new TextView(_context);
		_city.setId(32);
		_city.setText("Address:" + pCity);
		
		_bizURL = new TextView(_context);
		_bizURL.setId(33);
		_bizURL.setText("URL:" + pBizURL);		
		
		this.addView(_bizName);
		this.addView(_address);
		this.addView(_city);
		this.addView(_bizURL);	
	}
	
}