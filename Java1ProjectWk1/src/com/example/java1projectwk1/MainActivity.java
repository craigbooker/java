package com.example.java1projectwk1;

import android.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
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
	//Spinner spinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(lp);
        
        TextView tv = new TextView(this);
        tv.setText("Lookup error codes.");
        ll.addView(tv);
        
        Spinner spin = (Spinner) findViewById(R.id.spinner);
		spin.setOnItemSelectedListener(this);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
        		  this, R.array.pid_array, android.R.layout.simple_spinner_item );
        		adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
     
     
     /*
     Spinner spinner = new Spinner(this);
     ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
     spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

     spinner = (Spinner) findViewById( R.id.spinner );
     spinner.setAdapter(spinnerArrayAdapter);
     */
        
        Spinner spinnerCountPID = (Spinner)findViewById(R.id.spinnercountpid);
        ArrayAdapter<String> spinnerCountPIDArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.pid_array));
        spinnerCountPID.setAdapter(spinnerCountPIDArrayAdapter);
        
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        
     ll.addView(spinner);
        et = new EditText(this);
        et.setHint("Select Trouble Code.");
        
        Button b = new Button(this);
        b.setText("Look Up");
        
        b.setOnClickListener(new View.OnClickListener() {
    		@Override
    		public void onClick(View v) {
				int quarter = getResources().getStringArray(R.array.pid_array);
				
				int entry = Integer.parseInt(et.getText().toString());
				
				//int numQ = (100/quarter)*entry;

				
				result.setText("Quarter: " + numQ + "\r\n" + 
						"Dime: " + numD + "\r\n" + 
						"Nickel: " + numN + "\r\n" + 
						"Penny: " + numP + "\r\n"
						);
			}
		});
	}
	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		selection.setText(items[position]);
	}

	public void onNothingSelected(AdapterView<?> parent) {
		selection.setText("");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
