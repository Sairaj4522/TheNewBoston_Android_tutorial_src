package com.thenewboston.travis;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class OpenedClass extends Activity implements OnCheckedChangeListener{
	
	TextView question, test;
	Button returnData;
	RadioGroup selectionList;
	String gotBread, sendData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send);
		initialize();
		
		SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String et = getPrefs.getString("name", "Travis is...");
		String values = getPrefs.getString("list", "4");
		if(values.contentEquals("1")){
			question.setText(et);
		}
		
		//Bundle gotBasket = getIntent().getExtras();
		//gotBread = gotBasket.getString("bread");
		//question.setText(gotBread);
	}
	private void initialize() {
		returnData = (Button) findViewById(R.id.bReturn);
		question = (TextView) findViewById(R.id.tvQuestion);
		test = (TextView) findViewById(R.id.tvTest);
		selectionList = (RadioGroup) findViewById(R.id.rgAnswers);
		selectionList.setOnCheckedChangeListener(this);
	}
	
	public void onReturnButtonClick(View view){
		Intent person = new Intent();
		Bundle backpack = new Bundle();
		backpack.putString("answer", sendData);
		person.putExtras(backpack);
		setResult(RESULT_OK, person);
		finish();
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId){
			case R.id.rCrazy:
				sendData = "Probably right!";
				break;
				
			case R.id.rSexy:
				sendData = "Definitely right!";
				break;
				
			case R.id.rBoth:
				sendData = "Spot on!";
				break;
		}
		
		test.setText(sendData);
	}
}
