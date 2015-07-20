package com.thenewboston.travis;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SharedPrefs extends Activity implements OnClickListener {

	EditText sharedData;
	TextView dataResults;

	
	public final static String fileName = "MySharedString";
	SharedPreferences someData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sharedpreferences);
		setupVariables();
		someData = getSharedPreferences(fileName, 0);
		
	}

	private void setupVariables() {
		Button save = (Button) findViewById(R.id.bSave);
		Button load = (Button) findViewById(R.id.bLoad);
		sharedData = (EditText) findViewById(R.id.etSharedPrefs);
		dataResults = (TextView) findViewById(R.id.tvLoadSharedPrefs);
		save.setOnClickListener(this);
		load.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.bSave:
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(sharedData.getWindowToken(), 0);
				
				String stringData = sharedData.getText().toString();
				SharedPreferences.Editor editor = someData.edit();
				editor.putString("sharedString", stringData);
				
				if(editor.commit()){
					Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
				}
				
				break;
				
			case R.id.bLoad:
				someData = getSharedPreferences(fileName, 0);
				String dataReturned = someData.getString("sharedString", "Couldn't load Data").toString();
				dataResults.setText(dataReturned);
				break;
		}
	}

}
