package com.thenewboston.travis;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class StartingPoint extends Activity {

	private int counter;
	Button add, sub;
	TextView display;

	public void onCreate(Bundle savedInstaceState){
		super.onCreate(savedInstaceState);
		setContentView(R.layout.main);

		counter = 0;
		add = (Button) findViewById(R.id.bAdd);
		sub = (Button) findViewById(R.id.bSub);
		display = (TextView) findViewById(R.id.tvDisplay);
		
		AdView ad = (AdView) findViewById(R.id.ad);
		ad.loadAd(new AdRequest());
		
		add.setOnClickListener(new View.OnClickListener(){
						public void onClick(View view){
							counter++;
							display.setText("Your total is " + counter);
						}			
					}
		);

		sub.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view){
				counter--;
				display.setText("Your total is " + counter);
			}
		});
	}
}
