package com.craigbooker.autoservicefinder;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.util.Log;

public class Main  extends Activity {

	Context _context;
	HashMap<String, String> _history;
	String _favorites;
	static final int REQUEST_CODE = 0;
	
	private Handler searchHandler = new Handler(){
		public void handleMessage(Message message){
			Object path = message.obj;
			if(message.arg1 == RESULT_OK && path != null) {
				JSONObject json = buildJSON((String) message.obj);
				try{
					JSONObject results = json.getJSONObject("query").getJSONObject("results").getJSONObject("row");
				} catch (JSONException e){
					Log.e("JSON Exception", "Error parsing repsonse");
				}
			}
		}
	};
	
	public void updateData(JSONObject data){
		try{
			
		} catch(JSONException e){
			Log.e("JSON ERROR", e.toString());
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	private void getSearch(String category){
		
	}
	
	@Override
	protected void onActivityResult(){
		if(resultCode == RESULT_OK && requestCode = REQUEST CODE){
			if(data.hasExtra("category")){
				String category = data.getExtras().getString("category");
				((EditText) findViewById(R.id.searchField)).setText(category);
				getSearch(category);
			}
		}
	};
}
