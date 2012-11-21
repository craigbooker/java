package com.example.java1projectwk1;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
	LinearLayout ll;
	LinearLayout.LayoutParams lp;
	EditText et;
	TextView result;
	Spinner main_spinner;

	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(lp);
        
        TextView tv = new TextView(this);
        //TextView result = null;
        //tv.setText(getString(R.string.quarter)+","+getString(R.string.dime)+","+getString(R.string.nickel)+","+getString(R.string.penny));
        tv.setText("Translate trouble code to something useful.");
        tv.setTextSize(14);
        
        ll.addView(tv);
        main_spinner = new Spinner(this);
      
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.pid_string_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        
        main_spinner.setAdapter(adapter);
        
        
        ArrayAdapter<CharSequence> sensorAdapter = ArrayAdapter.createFromResource(this, R.array.sensor_string_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        
        
        et = new EditText(this);
        et.setHint("Enter Code.");
        //ll.addView(et);
        
        Button b = new Button(this);
        b.setText("Lookup Code");
        //ll.addView(b);
        
        
        
        /*
        b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int quarter = getResources().getInteger(R.array.pid_string_array);
				int dime = getResources().getInteger(R.integer.dime);
				int nickel = getResources().getInteger(R.integer.nickel);
				int penny = getResources().getInteger(R.integer.penny);
				
				int entry = Integer.parseInt(et.getText().toString());
				
				int numQ = (100/quarter)*entry;
				int numD = (100/dime)*entry;
				int numN = (100/nickel)*entry;
				int numP = (100/penny)*entry;
				
				result.setText("Quarter: " + numQ + "\r\n" + 
						"Dime: " + numD + "\r\n" + 
						"Nickel: " + numN + "\r\n" + 
						"Penny: " + numP + "\r\n"
						);
			}
		});
        */
        
        LinearLayout form = new LinearLayout(this);
        form.setOrientation(LinearLayout.HORIZONTAL);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        form.setLayoutParams(lp);
        
        form.addView(main_spinner);
        form.addView(b);
        
        ll.addView(form);
        
        
        result = new TextView(this);
        ll.addView(result);
        
        setContentView(ll);
    }
    public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    	
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}

