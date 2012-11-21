package com.example.java1projectwk1;



import android.os.Bundle;
import android.app.Activity;
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
	LinearLayout ll;
	LinearLayout.LayoutParams lp;
	EditText et;
	TextView spinnerLabel;
	TextView result;
	Spinner main_spinner;
	String[ ] sensorsArray;
	String[ ] solutionsArray;
	int clickCounter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(lp);
        sensorsArray = getResources().getStringArray(R.array.sensor_string_array);
        solutionsArray = getResources().getStringArray(R.array.solution_string_array);
        Button b = new Button(this);
        b.setText("Lookup Possible Problem");
        b.setOnClickListener(new View.OnClickListener() {
			
 			@Override
 			public void onClick(View v) {
 				if(clickCounter > -1) {
 					for(int i = 0; i < solutionsArray.length; i++) {
 						Toast.makeText(getBaseContext(),
 		                        "You might need to: " + solutionsArray[i],
 		                        Toast.LENGTH_LONG).show();
 		            }	
 	
 				}
 			}
 		});
        
        TextView tv = new TextView(this);
        //TextView result = null;
        //tv.setText(getString(R.string.quarter)+","+getString(R.string.dime)+","+getString(R.string.nickel)+","+getString(R.string.penny));
        tv.setText("Translate trouble code to something useful.");
        tv.setTextSize(14);
        
        spinnerLabel = new TextView(this);
        spinnerLabel.setText("Select a trouble code.");
        
        ll.addView(tv);
        main_spinner = new Spinner(this);
      
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.pid_string_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        
        main_spinner.setAdapter(adapter);
        main_spinner.setOnItemSelectedListener (new OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        		int index = arg0.getSelectedItemPosition();
            	Toast.makeText(getBaseContext(),
                        "You have selected item : " + sensorsArray[index],
                        Toast.LENGTH_LONG).show();
                  }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getBaseContext(),
                        "You need to select an item. ",
                        Toast.LENGTH_LONG).show();
                  }
	
        });
        
        
        ArrayAdapter<CharSequence> sensorAdapter = ArrayAdapter.createFromResource(this, R.array.sensor_string_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        
        
        et = new EditText(this);
        et.setHint("Enter Code.");
        //ll.addView(et);
        

        
        
        
 
        
        LinearLayout form = new LinearLayout(this);
        form.setOrientation(LinearLayout.HORIZONTAL);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        form.setLayoutParams(lp);
        
        form.addView(spinnerLabel);
        form.addView(main_spinner);
        
        
        ll.addView(form);

        ll.addView(b);
        
        result = new TextView(this);
        ll.addView(result);
        
        setContentView(ll);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
	}
