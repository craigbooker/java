package FormStuff;

import android.content.Context;
import android.widget.LinearLayout;


public class FormBorder extends LinearLayout {
	LinearLayout _ll;
	
	
	public FormBorder(Context context){
		super(context);
		
		LayoutParams _lp;
		
		_ll = new LinearLayout(context);
		_lp = new LayoutParams(LayoutParams.MATCH_PARENT, 5);
		_ll.setLayoutParams(_lp);
		
		this.addView(_ll);
	}
}