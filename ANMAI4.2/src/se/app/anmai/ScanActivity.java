package se.app.anmai;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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
    private static final String tag ="DEBUG_SCANACTIVITY";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.linearlay);
		flagcontent = this.getResources().getStringArray(R.array.nameOfFlags);
		listView = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<String> adapter = new ListViewAdapter(this, flagcontent);
		listView.setAdapter(adapter);
      
	}
    private boolean getFlagstate(String flag){
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
    	return prefs.getBoolean(flag, false);
    }
	private boolean[] getFlagsValues() {
		boolean[] flagstates = new boolean[flagcontent.length];
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		for (int i = 0; i < flagcontent.length; i++) {
			boolean state = prefs.getBoolean(flagcontent[i], false);
			flagstates[i] = state;
			Log.d(tag, "FLAG "+ flagcontent[i] +" is set to " + state);
		}
		return flagstates;
	}

	// SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
	// editor.putString("text", mSaved.getText().toString());
	// editor.putInt("selection-start", mSaved.getSelectionStart());
	// editor.putInt("selection-end", mSaved.getSelectionEnd());
	// editor.commit();
	// To retrieve data from shared preference
	//
	// SharedPreferences prefs = getPreferences(MODE_PRIVATE);
	// String restoredText = prefs.getString("text", null);
	// if (restoredText != null)
	// {
	// //mSaved.setText(restoredText, TextView.BufferType.EDITABLE);
	// int selectionStart = prefs.getInt("selection-start", -1);
	// int selectionEnd = prefs.getInt("selection-end", -1);
	// /*if (selectionStart != -1 && selectionEnd != -1)
	// {
	// mSaved.setSelection(selectionStart, selectionEnd);
	// }*/
	// }
	public void artiklescannen(View view) {
		setContentView(R.layout.scanner_layout);
	}

	public void onClick(View view) {
		IntentIntegrator integrator = new IntentIntegrator(this);
		integrator.initiateScan();
	}



	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		if (scanResult != null) {
			String barcode;
			String typ;

			barcode = scanResult.getContents();
			typ = scanResult.getFormatName();

			EditText etBarcode = (EditText) findViewById(R.id.etBarcode);
			EditText etTyp = (EditText) findViewById(R.id.etTyp);

			etBarcode.setText(barcode.toString());
			etTyp.setText(typ.toString());

		}
	}

}
