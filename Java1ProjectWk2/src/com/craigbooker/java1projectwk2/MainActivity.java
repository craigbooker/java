package com.craigbooker.java1projectwk2;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final String TAG = MainActivity.class.getSimpleName();
	LinearLayout ll;
	LinearLayout.LayoutParams lp;

	TextView radiusLabel;
	EditText etRadius;
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

        /*
        LinearLayout ll = new LinearLayout(this);
        
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
        
        
        ll.addView(milesEntryBox);
        ll.setOrientation(LinearLayout.VERTICAL);
        */
    	Log.i("I made it to: ", "troubleCodeSpinner start");
        LinearLayout troubleCodeSpinner = FormThings.singleSpinnerWithLabel(this, "Select a Trouble Code");
    	Log.i("I made it to: ", "troubleCodeSpinner end.");
        TextView troubleCodeLabel = (TextView) troubleCodeSpinner.findViewById(3);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.pid_string_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        Spinner codeSpinner = (Spinner) troubleCodeSpinner.findViewById(4);
        		
        codeSpinner.setOnItemSelectedListener (new OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        		int index = arg0.getSelectedItemPosition();
        		selectedIndex = index;
        		selectedTroubleCode = pidArray[selectedIndex];
        		selectedSensor = sensorsArray[selectedIndex];
        		correspondingSolution = solutionsArray[selectedIndex];
            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				Toast.makeText(getBaseContext(),
                        "You need to select an item. ",
                        Toast.LENGTH_LONG).show();
            }
	
        });
        /*
        ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(lp);
        */
        pidArray = getResources().getStringArray(R.array.pid_string_array);
        sensorsArray = getResources().getStringArray(R.array.sensor_string_array);
        solutionsArray = getResources().getStringArray(R.array.solution_string_array); 

        /*
        Button b = new Button(this);
        b.setText("Show Trouble Code Info");
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
        
        //spinnerLabel = new TextView(this);
        //spinnerLabel.setText("Select trouble code");
        
        ll.addView(tv);
        

 
        //form.addView(main_spinner);
        //ll.addView(form);
        //ll.addView(b);
 
        result = new TextView(this);
        ll.addView(result);
        
        etRadius = new EditText(this);
        etRadius.setHint("Enter Miles.");
        
        radiusLabel = new TextView(this);
        radiusLabel.setText("Enter a radius.");
        

        LinearLayout radiusForm = new LinearLayout(this);
        radiusForm.setOrientation(LinearLayout.HORIZONTAL);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        radiusForm.setLayoutParams(lp);
        
        radiusForm.addView(radiusLabel);
        radiusForm.addView(etRadius);
        ll.addView(radiusForm);
        Button findShopButton = new Button(this);
        findShopButton.setText("Show Repair Shop Info");
        findShopButton.setOnClickListener(new View.OnClickListener() {
  	 			@Override
   			public void onClick(View v) {
   				//solutionsArray = getResources().getStringArray(R.array.solution_string_array);	
   				if(selectedTroubleCode != null) {
   					
   				}else if (selectedTroubleCode.compareTo("00") == 0){
   					Toast.makeText(getBaseContext(),
   	                        "You need to select an item. ",
   	                        Toast.LENGTH_LONG).show();
   				} else {

   				result.setText("Trouble Code: " + selectedTroubleCode + "\r\n" + 
  						"Sensor Type: " + selectedSensor + "\r\n" + 
  						"Possible solution: " + correspondingSolution + "\r\n"
  						);
   				}

  	 			}
   			
   		});
        ll.addView(findShopButton);

        
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
