package com.example.java1projectwk1;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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
	//String[ ] listSolutionsArray;  This was added in to show the use of a for loop to fill an empty array.
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
		if((listSolutionsArray.length == 0) || (listSolutionsArray == null)) {
			Toast.makeText(getBaseContext(),
                    "You needto fill your solutionsArray",
                    Toast.LENGTH_LONG).show();
			// This will fill the solutions array using a for loop to print out later.
			for (int i=0; i < solutionsArray.length; i++) {
				listSolutionsArray[i] = solutionsArray[i];
				Log.d("I am in the fillarray", correspondingSolution);
			}
		}
	*/	
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
        		int index = arg0.getSelectedItemPosition();
        		selectedIndex = index;
        		selectedTroubleCode = pidArray[selectedIndex];
        		selectedSensor = sensorsArray[selectedIndex];
        		correspondingSolution = solutionsArray[selectedIndex];
            	Toast.makeText(getBaseContext(),
                        "You have selected item : " + sensorsArray[index],
                        Toast.LENGTH_LONG).show();
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
        ll.addView(b);
        
        result = new TextView(this);
        ll.addView(result);
        
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
