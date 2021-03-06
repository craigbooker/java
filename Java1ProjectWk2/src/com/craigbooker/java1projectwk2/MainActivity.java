package com.craigbooker.java1projectwk2;

import java.util.ArrayList;

import com.craigbooker.diags.Location;
import com.craigbooker.diags.MaintenanceRepair;
import com.craigbooker.lib.FormThings;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final String TAG = MainActivity.class.getSimpleName();
	LinearLayout ll;
	LinearLayout.LayoutParams lp;
	TextView spinnerLabel;
	TextView result;
	Spinner main_spinner;
	String selectedTroubleCode;
	String selectedSensor;
	String correspondingSolution;
	String[ ] pidArray;
	String[ ] sensorsArray;
	String[ ] solutionsArray;
	int selectedIndex;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(lp);
        pidArray = getResources().getStringArray(R.array.pid_string_array);
        sensorsArray = getResources().getStringArray(R.array.sensor_string_array);
        solutionsArray = getResources().getStringArray(R.array.solution_string_array); 

        /*
        Button b = new Button(this);
        b.setText("Lookup Possible Problem");
        b.setOnClickListener(new View.OnClickListener() {
	 			@Override
 			public void onClick(View v) {
 				//solutionsArray = getResources().getStringArray(R.array.solution_string_array);	
 				if(selectedTroubleCode != null){

 				result.setText("Trouble Code: " + selectedTroubleCode + "\r\n" + 
						"Sensor Type: " + selectedSensor + "\r\n" + 
						"Possible solution: " + correspondingSolution + "\r\n"
						);

	 			}
 			}
 		});
 		*/
        TextView tv = new TextView(this);
        tv.setText("Translate trouble code to something useful.");
        tv.setTextSize(14);
        
        spinnerLabel = new TextView(this);
        spinnerLabel.setText("Select a trouble code.");
        
        ll.addView(tv);
        LinearLayout form = new LinearLayout(this);
        form.setOrientation(LinearLayout.HORIZONTAL);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        form.setLayoutParams(lp);
        form.addView(spinnerLabel);
        main_spinner = new Spinner(this);
      
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.pid_string_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        
        main_spinner.setAdapter(adapter);
        main_spinner.setOnItemSelectedListener (new OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        		Log.i("I made it to:", " Start of onItemSelected.");
        		int index = arg0.getSelectedItemPosition();
        		selectedIndex = index;
        		selectedTroubleCode = pidArray[selectedIndex];
        		selectedSensor = sensorsArray[selectedIndex];
        		correspondingSolution = solutionsArray[selectedIndex];
        		
    			if(selectedTroubleCode == null){
    				Toast.makeText(getBaseContext(),
                            "You need to select an item....null ",
                            Toast.LENGTH_LONG).show();   				
    				
    			} else if (selectedTroubleCode == "00") {
    				Toast.makeText(getBaseContext(),
                            "You need to select an item..00 ",
                            Toast.LENGTH_LONG).show();
    				
    			} else {
     				result.setText("Trouble Code: " + selectedTroubleCode + "\r\n" + 
    						"Sensor Type: " + selectedSensor + "\r\n" + 
    						"Possible solution: " + correspondingSolution + "\r\n"
    						);

    	 		}
        		/*
            	Toast.makeText(getBaseContext(),
                        "You have selected item : " + sensorsArray[index],
                        Toast.LENGTH_LONG).show();
                        */
            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				Toast.makeText(getBaseContext(),
                        "You need to select an item. ",
                        Toast.LENGTH_LONG).show();
            }
	
        });
 
        form.addView(main_spinner);
        ll.addView(form);
        result = new TextView(this);
        ll.addView(result);
        //ll.addView(b);
        
        
        LinearLayout milesEntryLl = new LinearLayout(this);
        
        LinearLayout milesEntryBox = FormThings.singleEntryWithButton(this, "Enter Miles", "Go");
        //EditText miles = (EditText) milesEntryBox.findViewById(1);
        Button milesButton = (Button) milesEntryBox.findViewById(2);
        
        
        milesButton.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v){ 
        		
        		EditText milesText = (EditText) v.getTag();
        		Log.i("BUTTON CLICKED: ", milesText.getText().toString());
        	} 
        	
        });
        ArrayList<Location> locations = new ArrayList<Location>();
        locations.add(new MaintenanceRepair("John's Autobody", "-120", "45"));
        locations.add(new MaintenanceRepair("Midas", "-120", "45"));
        locations.add(new MaintenanceRepair("George's Garage", "-120", "45"));
        locations.add(new MaintenanceRepair("Christian Brothers Automotive", "-120", "45"));
        locations.add(new MaintenanceRepair("Kwik Kar", "-120", "45"));
		
		String[] locationNames = new String[locations.size()];
		for(int i=0; i<locations.size(); i++){
			locationNames[i] = locations.get(i).getName();
		}
        
		RadioGroup locationOptions = FormThings.getOptions(this, locationNames);
		ll.addView(locationOptions);
        milesEntryLl.addView(milesEntryBox);
        milesEntryLl.setOrientation(LinearLayout.VERTICAL);
        ll.addView(milesEntryLl);
        
        setContentView(ll);
        Log.d(TAG, "We are logging from the onCreate() mehtod.");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
 
}
