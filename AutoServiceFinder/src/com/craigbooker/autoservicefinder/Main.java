package com.craigbooker.autoservicefinder;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.craigbooker.lib.FileStuff;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;

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
	
	private JSONObject buildJSON(String jsonString){
		JSONObject data;
		try{
			
		} catch (JSONException e){
			data = null;
		}
		return data;
	}
	
	Button favButton = (Button) findViewById(R.id.favButton);
		// ADD FAVORITE BUTTON
		Button addFav = (Button) findViewById(R.id.addFavsButton);
		addFav.setOnClickListener(new OnClickListener() {
			@Overrride
			public void onClick(View v){
				String currentCategory = ((TextView) findViewById(R.id.searchField)).getText().toString();
				if(currentCategory != null){
					if(_favorites.length() > 0){
						_favorites = _favorites.concat("," +currentCategory);
					} else {
						_favorites = currentCategory;
					}
					FileStuff.storeStringFile(_context, "favorites", _favorites, true);
				}
			}
		});
	}
	@SuppressWarnings("unchecked")
	private HashMap<String, String> getHistory(){
		Object stored = FileStuff.readObjectFile(_context, "history", false);
		
		HashMap<String, String> history;
		if(stored == null){
			Log.i("HISTORY", "NO HISTORY FILE FOUND");
			history = new HashMap<String, String>();
		} else {
			history = (HashMap<String, String>) stored;
		}
		return history;
	}
	
	private void getSearch(String category){
		Messenger messenger = new Messenger(quoteHandler);
		Intent intent = new Intent(_context, GetYelpSearch.class);
		intent.putExtra("category", category);
		intent.putExtra("messenger", messenger);
		startService(intent);
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
