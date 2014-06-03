package com.example.internet_connection_app;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

public class ImageLoader extends AsyncTask<String, Void, Drawable>{
	
	Drawable _image;
    private String _url;
    Activity myActivity;
    public ImageLoader(Activity myActivity) {
		this.myActivity=myActivity;
	}
	@Override
	protected Drawable doInBackground(String... params) {
         _url = params[0];
         InputStream istr = null;
			try {
			URL url = new URL(_url);
			istr = url.openStream();
			} 
			catch (MalformedURLException e) {
             e.printStackTrace();
			} 
            catch (IOException e) {
			e.printStackTrace();
		}
		return Drawable.createFromStream(istr, "src");
	}
	
	@Override
	protected void onPostExecute(Drawable result) {
		super.onPostExecute(result);
		_image = result;
		//myActivity setting image to imageview
	}
	
}
