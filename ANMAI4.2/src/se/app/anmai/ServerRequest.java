package se.app.anmai;

import java.io.BufferedReader;
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
    
	//TestEAN
    //	private final String testEAN= "3045320092066";
	private ResultActivity ac;
	private static final String DEBUG_TAG = "HttpConnection";

	public ServerRequest(String barcode, ResultActivity ac) {
		this.ac = ac;
		ConnectivityManager connMgr = (ConnectivityManager) this.ac
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			new DataBaseRequest().execute(serverURL + barcode);
//			new DataBaseRequest().execute(serverURL+testEAN);

		} else {
			Toast.makeText(this.ac, "No network connection available.",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * String - type of input Void - empty String - type of output
	 * 
	 * @author Sergej Gorr
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
			if (result !=null && result.getPictureURI() != null) {
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

		StandardExchangeArticle exa = null;
		try {
			URL url = new URL(myurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			StringBuffer stringbuffer = new StringBuffer();
			String line = null;
			BufferedReader bufferReader = null;

			bufferReader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			while ((line = bufferReader.readLine()) != null) {
				stringbuffer.append(line);
			}

			String contentAsString = stringbuffer.toString();
			Log.d(DEBUG_TAG, "Result " + contentAsString);
			// =contentAsString;
			exa = Deserializer.deserializeStandardArticle(contentAsString);
            if(exa!=null){
			Log.d(DEBUG_TAG, "Description " + exa.getDescription());
			Log.d(DEBUG_TAG, "Name " + exa.getName());
			Log.d(DEBUG_TAG, "URL " + exa.getPictureURI());
			Log.d(DEBUG_TAG, "FLAGS " + exa.getFlags().toString());
            }
			if (conn != null) {
				conn.disconnect();
			}

		} catch (Exception ex) {
			return exa;
		}
		return exa;
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
