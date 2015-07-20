package com.thenewboston.travis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ExternalData extends Activity implements OnItemSelectedListener, OnClickListener {
	
	TextView canWrite, canRead;
	String state;
	boolean canW,canR;
	Spinner spinner;
	String[] paths = {"Music", "Pictures", "Download"};
	File path = null;
	File file = null;
	EditText saveFile;
	Button confirm, save;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.externaldata);
		canWrite = (TextView) findViewById(R.id.tvCanWrite);
		canRead = (TextView) findViewById(R.id.tvCanRead);
		confirm = (Button) findViewById(R.id.bConfirmSaveAs);
		save = (Button) findViewById(R.id.bSaveFile);
		saveFile = (EditText) findViewById(R.id.etSaveAs);
		canWrite.setText("false");
		canRead.setText("false");
		
		state = Environment.getExternalStorageState();
		
		confirm.setOnClickListener(this);
		save.setOnClickListener(this);
		
		checkState();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(ExternalData.this, 
				android.R.layout.simple_spinner_item, paths);
		spinner = (Spinner) findViewById(R.id.spinner1);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}


	private void checkState() {
		
		if(state.equals(Environment.MEDIA_MOUNTED)){
			//read and write
			canWrite.setText("true");
			canRead.setText("true");
			canR  = canW = true;
		} else if(state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
			//read but can't write
			canWrite.setText("false");
			canRead.setText("true");
			canW = false;
			canR = true;
		} else {
			canWrite.setText("false");
			canRead.setText("false");
			canR = canW = false;
		}
	}


	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		int position = spinner.getSelectedItemPosition();
		
		switch(position){
			case 0:
				path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
				break;
			
			case 1:
				path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
				break;
			
			case 2:
				path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
				break;
		}
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}


	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.bConfirmSaveAs:
				save.setVisibility(View.VISIBLE);
				break;
				
			case R.id.bSaveFile:
				String f = saveFile.getText().toString();
				file = new File(path, f + ".png");
				
				checkState();
				if(canW == canR == true){
					path.mkdirs();
					
					try {
						InputStream is = getResources().openRawResource(R.drawable.greenball);
						OutputStream os = new FileOutputStream(file);
						
						byte[] data = new byte[is.available()];
						is.read(data);
						os.write(data);
						
						is.close();
						os.close();
						
						Toast.makeText(ExternalData.this, "File has been saved", Toast.LENGTH_SHORT).show(); 
						//Update files for in the media gallery
						MediaScannerConnection.scanFile(ExternalData.this, new String[] {file.toString()},
								null,
								new MediaScannerConnection.OnScanCompletedListener() {
									
									@Override
									public void onScanCompleted(String path, Uri uri) {
										Toast.makeText(ExternalData.this, "scan complete", Toast.LENGTH_SHORT).show();
									}
								});
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				
				}
				break;
		}
	}
	
}
