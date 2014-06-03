package se.app.anmai;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 
 * @author Sergej Gorr
 * @author Siourkul Zhooshbaev
 * 
 */
public class ScanActivity extends Activity {

	private String[] flagcontent;

	public static final String PERSFLAGS = "flags";
	private ListView listView;
	private static final String tag = "DEBUG_SCANACTIVITY";
	public static final String BARCODE_EXTRA = "BARCODE";
	public static final String BARCODE_TYPE_EXTRA = "BARCODE_TYPE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.linearlay);
		flagcontent = this.getResources().getStringArray(R.array.nameOfFlags);
		listView = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<String> adapter = new ListViewAdapter(this, flagcontent);
		listView.setAdapter(adapter);

	}

	private boolean getFlagstate(String flag) {
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		return prefs.getBoolean(flag, false);
	}

	private boolean[] getFlagsValues() {
		boolean[] flagstates = new boolean[flagcontent.length];
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		for (int i = 0; i < flagcontent.length; i++) {
			boolean state = prefs.getBoolean(flagcontent[i], false);
			flagstates[i] = state;
			Log.d(tag, "FLAG " + flagcontent[i] + " is set to " + state);
		}
		return flagstates;
	}

	/**
	 * checks the internet connection 
	 * if there is internet connection executes scanner
	 * otherwise showing alert message.
	 * 
	 * @param view
	 */
	public void artiklescannen(View view) {
		ConnectivityManager connMgr = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			IntentIntegrator integrator = new IntentIntegrator(this);
			integrator.initiateScan();
		} else {
			Toast.makeText(this, "No network connection available.",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		if (scanResult != null) {
			String barcode;
			String typ;

			barcode = scanResult.getContents();
			typ = scanResult.getFormatName();

			Intent i = new Intent(this, ResultActivity.class);
			i.putExtra(BARCODE_EXTRA, barcode);
			i.putExtra(BARCODE_TYPE_EXTRA, typ);
			startActivity(i);


		}
	}
   @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	// TODO Auto-generated method stub
	return super.onKeyDown(keyCode, event);
}
}
