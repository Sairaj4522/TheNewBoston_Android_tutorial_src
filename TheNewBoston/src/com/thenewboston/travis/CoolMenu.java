package com.thenewboston.travis;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CoolMenu extends ListActivity {

	String classes[] = {"StartingPoint","TextPlay","Email",
			"Camera","NewCamera","Data","GFX","GFXSurface", "SoundStuff",
			"Slider", "Tabs", "SimpleBrowser", "Flipper","SharedPrefs",
			"InternalData", "ExternalData", "SQLiteExample", "Accelerate",
			"HttpExample", "WeatherXMLParsing", "GLExample","GLCubeEx",
			"Voice","TextVoice","StatusBar","SeekBarVolume"};
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		String cheese = classes[position];
		try {
			Class<?> ourClass = Class.forName("com.thenewboston.travis."+ cheese);
			Intent ourIntent = new Intent(CoolMenu.this, ourClass);
			startActivity(ourIntent);
		} catch (ClassNotFoundException e) {
			Log.d("Error","" + e.toString());
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Removing title and go full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classes));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.cool_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
		
			case R.id.aboutUs:
				Intent i = new Intent("com.thenewboston.travis.ABOUT");
				startActivity(i);
				break;
			case R.id.preferences:
				Intent pIntent = new Intent("com.thenewboston.travis.PREFS");
				startActivity(pIntent);
				break;
			case R.id.exit:
				finish();
				break;
		}
		
		return false;
	}
	
}
