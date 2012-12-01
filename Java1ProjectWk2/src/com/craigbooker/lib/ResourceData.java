package com.craigbooker.lib;

import com.craigbooker.java1projectwk2.R;
import android.content.Context;
import android.util.Log;

public class ResourceData {
	
	public String getArrayResourceData(Context context, String resourceName) {
		String getStatement = ("getResources().getStringArray(R.array." + resourceName + ");");
		Log.i("Statement Built:", getStatement);
		//String[] resourceStringArray = (String[]) (getStatement);
		return getStatement;
	}
	 //pidArray = getResources().getStringArray(R.array.pid_string_array);

}
