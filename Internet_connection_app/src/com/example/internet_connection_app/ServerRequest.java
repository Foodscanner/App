package com.example.internet_connection_app;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import deserializer.Deserializer;
import deserializer.StandardExchangeArticle;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class ServerRequest {

	BufferedReader response;
	Article article;
	String respond;
	TextView display;
	public static final String serverURL = "http://foodserver.cloudapp.net/ANMAIServer/ProductInformation.xml?ean=3045320092066";
	// if does not work: use "http://192.168.0.1/test.php"
	private MainActivity ac;
	private static final String DEBUG_TAG = "HttpConnection";

	public ServerRequest(String barCode, MainActivity ac) {
		this.ac = ac;
		this.display = ac.display;
		article = new Article();
		ConnectivityManager connMgr = (ConnectivityManager) this.ac
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			new DataBaseRequest().execute(serverURL);
		} else {
			Toast.makeText(this.ac, "No network connection available.",
					Toast.LENGTH_SHORT).show();
		}
	}

	public BufferedReader getResponse() {
		return this.response;
	}

	/**
	 * String - type of input Void - empty String - type of output
	 * 
	 * @author 111
	 * 
	 */
	private class DataBaseRequest extends AsyncTask<String, Void, StandardExchangeArticle> {

		@Override
		protected StandardExchangeArticle doInBackground(String... urls) {
			try {
				return downloadArtInfo(urls[0]);
			} catch (IOException e) {
				return new StandardExchangeArticle();
			}

		}

		@Override
		protected void onPostExecute(StandardExchangeArticle result) {
			ac.display.setText(result.getDescription());
            ac.fillArticleInfo(result);
		}
	}

	// Given a URL, establishes an HttpUrlConnection and retrieves
	// the web page content as a InputStream, which it returns as
	// a string.
	private StandardExchangeArticle downloadArtInfo(String myurl) throws IOException {
		Article art = new Article();
        StandardExchangeArticle ex = null ; 
		try {
			URL url = new URL(myurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			StringBuffer stringbuffer = new StringBuffer();
			String line = null;
			BufferedReader bufferReader = null;

			bufferReader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			while ((line = bufferReader.readLine()) != null) {
				// System.out.println(line);
				stringbuffer.append(line);
			}

			String contentAsString = stringbuffer.toString();
			// Log.d(DEBUG_TAG, "Result "+ contentAsString);
			// =contentAsString;
			ex= Deserializer
					.deserializeStandardArticle(contentAsString);
			art._description = ex.getDescription();
			Log.d(DEBUG_TAG, "Description " + ex.getDescription());
			Log.d(DEBUG_TAG, "Name " + ex.getName());
			Log.d(DEBUG_TAG, "URL " + ex.getPictureURI());
			Log.d(DEBUG_TAG, "FLAGS " + ex.getFlags().toString());

			if (conn != null) {
				conn.disconnect();
			}
		} catch (Exception e) {

		}
		return ex;
	}

	public String readIt(InputStream stream) throws IOException,
			UnsupportedEncodingException {
		Reader reader = null;
		reader = new InputStreamReader(stream, "UTF-8");
		int lengh = stream.available();
		char[] buffer = new char[lengh];
		reader.read(buffer);
		return new String(buffer);
	}

	private class ImageDowloader extends AsyncTask<String, Void, Drawable> {
		private String _url;

		@Override
		protected Drawable doInBackground(String... params) {
			_url = params[0];
			InputStream istr = null;

			try {
				URL url = new URL(_url);
				istr = url.openStream();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return Drawable.createFromStream(istr, "src");
		}

		@Override
		protected void onPostExecute(Drawable result) {
			super.onPostExecute(result);
			ServerRequest.this.article._image = result;
			// ac.findViewbyId(R.id.imagelayout).setDrawable(result)

		}
	}
}
