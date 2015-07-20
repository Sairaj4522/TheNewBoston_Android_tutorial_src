package com.thenewboston.travis;

//import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InternalData extends Activity implements OnClickListener {
	
	EditText sharedData;
	TextView dataResults;
	FileOutputStream fos;
	public static final String FILENAME = "InternalString";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sharedpreferences);
		setupVariables();
		
	}

	private void setupVariables() {
		Button save = (Button) findViewById(R.id.bSave);
		Button load = (Button) findViewById(R.id.bLoad);
		sharedData = (EditText) findViewById(R.id.etSharedPrefs);
		dataResults = (TextView) findViewById(R.id.tvLoadSharedPrefs);
		save.setOnClickListener(this);
		load.setOnClickListener(this);
		
		//The following code is for tutorial sake
		//When used it introduces problem data persistence
		//because this creates new file at every start up
		//and overwrites previous file
		/*try {
			fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.bSave:
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(sharedData.getWindowToken(), 0);
				String data = sharedData.getText().toString();
				//Saving data via File
				/*File f = new File(FILENAME);
				try {				
					fos = new FileOutputStream(f);
					//write some data
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				
				try {
					fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
					fos.write(data.getBytes());
					fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				break;
				
			case R.id.bLoad:
				new LoadSomeStuff().execute(FILENAME);
				break;
		}
	}
	
	public class LoadSomeStuff extends AsyncTask<String ,Integer, String>{
		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(InternalData.this);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setMax(100);
			dialog.show();
		}
		
		@Override 
		protected String doInBackground(String... params) {
			String collected = null;
			FileInputStream fis = null;
			
			for(int i = 0; i < 20; i++){
				publishProgress(5);
				
				try {
					Thread.sleep(88);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				fis = openFileInput(FILENAME);
				byte[] dataArray = new byte[fis.available()]; 
				while(fis.read(dataArray) != -1){
					collected = new String(dataArray);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				try {
					fis.close();
					return collected;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dataResults.setText(result);
			dialog.dismiss();
		}

		

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			dialog.incrementProgressBy(values[0]);
		}
		
	}
}
