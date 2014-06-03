package se.app.anmai;

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

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import deserializer.Deserializer;
import deserializer.StandardExchangeArticle;

/**
 * 
 * @author Sergej Gorr
 * 
 */
public class ServerRequest {


	public static final String serverURL = "http://foodserver.cloudapp.net/ANMAIServer/ProductInformation.xml?ean=";
	// if does not work: use "http://192.168.0.1/test.php"

	private String barcode;
	private ResultActivity ac;
	private static final String DEBUG_TAG = "HttpConnection";

	public ServerRequest(String barcode, ResultActivity ac) {
		this.ac = ac;
		this.barcode = barcode;
		ConnectivityManager connMgr = (ConnectivityManager) this.ac
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			new DataBaseRequest().execute(serverURL+barcode);
		} else {
			Toast.makeText(this.ac, "No network connection available.",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * String - type of input Void - empty String - type of output
	 * 
	 * @author 111
	 * 
	 */
	private class DataBaseRequest extends
			AsyncTask<String, Void, StandardExchangeArticle> {

		@Override
		protected StandardExchangeArticle doInBackground(String... urls) {
			try {
				return downloadArticleInfo(urls[0]);
			} catch (IOException e) {
				return new StandardExchangeArticle();
			}

		}

		@Override
		protected void onPostExecute(StandardExchangeArticle result) {
			ac.fillArticleInfo(result);
			if (result.getPictureURI() != null) {
				new ImageDowloader().execute(result.getPictureURI().toString());
			}
		}
	}

	/**
	 * Given a URL, establishes an HttpUrlConnection and retrieves the web page
	 * content as a InputStream, which it returns as a string.
	 * 
	 * @param myurl
	 * @return
	 * @throws IOException
	 */

	private StandardExchangeArticle downloadArticleInfo(String myurl)
			throws IOException {
		InputStream is = null;

		try {
			URL url = new URL(myurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			//MIME_TYPE taking from http://de.selfhtml.org/diverses/mimetypen.htm
			conn.setRequestProperty("Content-Type",
					"application/xml");
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestProperty("charset", "utf-8");
			conn.connect();
			DataOutputStream writer;

			writer = new DataOutputStream(conn.getOutputStream());
			writer.writeBytes(this.barcode);
			writer.flush();
			writer.close();

			// Starts the query

//			int response = conn.getResponseCode();
			is = conn.getInputStream();

			// Convert the InputStream into a string
			String contentAsString = readIt(is);
			Log.d(DEBUG_TAG, "InputStream's content: " + contentAsString);
			StandardExchangeArticle article = Deserializer
					.deserializeStandardArticle(contentAsString);

			if (conn != null) {
				conn.disconnect();
			}
			// Article
			if (article == null)
				article = new StandardExchangeArticle();
			return article;

			// Makes sure that the InputStream is closed after the app is
			// finished using it.

		} finally {
			if (is != null) {
				is.close();
			}

		}
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
			ac.setImage(result);

		}
	}
}
