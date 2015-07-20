package com.thenewboston.travis;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Camera extends Activity {
	ImageView iv;
	Button ib,b;
	private final static int TAKE_PICTURE_REQUEST = 0;
	Bitmap bmp;
	Handler handler;
	String imageFilePath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo);
		initialize();
		handler = new Handler();
		
		InputStream is = getResources().openRawResource(R.drawable.splash_background);
		bmp = BitmapFactory.decodeStream(is);
		
	}

	private void initialize() {
		iv = (ImageView) findViewById (R.id.ivReturnedPic);
		ib = (Button) findViewById(R.id.bTakePic);
		b = (Button) findViewById(R.id.bSetWall);
	}
	
	public void onTakePicButtonClick(View view){
		imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/myfavoritepicture.jpg";
		File imageFile = new File(imageFilePath);
		Uri imageFileUri = Uri.fromFile(imageFile);
		
		Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);
		startActivityForResult(i, TAKE_PICTURE_REQUEST);
	}
	
	public void onSetWallButtonClick(View view){
		Thread setWallThread = new Thread(new Runnable() {
					public void run(){
						WallpaperManager manager = WallpaperManager.getInstance(Camera.this);
						try {
							manager.setBitmap(bmp);
							
							handler.post(new Runnable(){

								@Override
								public void run() {
									Toast.makeText(Camera.this, "Wallpaper Set", Toast.LENGTH_SHORT).show();
								}
								
							});
						} catch (IOException e) {
							handler.post(new Runnable(){

								@Override
								public void run() {
									Toast.makeText(Camera.this, "Error setting wallpaper", Toast.LENGTH_SHORT).show();
								}
								
							});
							e.printStackTrace();
						}
					}
				});
			setWallThread.start();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == TAKE_PICTURE_REQUEST){
			if(resultCode == RESULT_OK){
				Display currentDisplay = getWindowManager().getDefaultDisplay();
				
				int dH = currentDisplay.getHeight();
				int dW = currentDisplay.getWidth();
				
				// Load up the image's dimensions not the image itself
				BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
				bmpFactoryOptions.inJustDecodeBounds = true;
				bmp = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);
				
				int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) dH);
				int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) dW);
				
				Log.v("HEIGHTRATIO","" + heightRatio);
				Log.v("WIDTHRATIO","" + widthRatio);
				
				// If both of the ratios are greater than 1,
				// one if the sides of the image is greater than the screen
				if(heightRatio > 1 && widthRatio > 1){
					if(heightRatio > widthRatio){
						// Height ratio is larger, scale according to it
						bmpFactoryOptions.inSampleSize = heightRatio;
					} else {
						// Width ratio is larger, scale according to it
						bmpFactoryOptions.inSampleSize = heightRatio;
					}
					
					if(heightRatio == widthRatio){
						// Can use either
						bmpFactoryOptions.inSampleSize = heightRatio;
					}
				}
				// Decode it for real
				bmpFactoryOptions.inJustDecodeBounds = false;
				bmp = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);
				
				// Display it
				iv.setImageBitmap(bmp);
			}
		}
	}
	
	
}
