package FormStuff;

import android.content.Context;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;

public class FormBorder {

	public static LinearLayout formBorder(Context context){
	
		LinearLayout ll = new LinearLayout(context);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 5);
		ll.setLayoutParams(lp);
		
		return ll;
	}
}