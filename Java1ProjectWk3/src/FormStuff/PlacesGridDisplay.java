package FormStuff;

import android.content.Context;
import android.widget.GridLayout;
import android.widget.TextView;
import FormStuff.FormBorder;

public class PlacesGridDisplay extends GridLayout {
		TextView _bizName;
		TextView _bizAddress;	
		TextView _bizCity;	
		TextView _bizURL;
		
		Context _context;

		public PlacesGridDisplay(Context context){
			super(context);
			
			
			_context = context;
			
			this.setColumnCount(2);
			
			
			TextView placeBizName = new TextView(_context);
			placeBizName.setText("Business: ");
			
			_bizName = new TextView(_context);
			_bizName.setId(34);

			TextView placeBizAddress = new TextView(_context);
			placeBizAddress.setText("Address: ");
			
			_bizAddress = new TextView(_context);
			_bizAddress.setId(35);			
			
			
			TextView placeBizCity = new TextView(_context);
			placeBizCity.setText("City: ");
			
			_bizCity = new TextView(_context);
			_bizCity.setId(36);			
			
			// BizURL
			TextView placeBizURL = new TextView(_context);
			placeBizURL.setText("URL: ");
			
			_bizURL = new TextView(_context);
			_bizURL.setId(37);			
			
			this.addView(placeBizName);
			this.addView(_bizName);

			this.addView(placeBizAddress);
			this.addView(_bizAddress);	
			
			this.addView(placeBizCity);
			this.addView(_bizCity);

			this.addView(placeBizURL);
			this.addView(_bizURL);
			
			FormBorder ll = new FormBorder(_context);
			this.addView(ll);
			
			
		}
}