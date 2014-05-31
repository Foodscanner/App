package se.app.anmai;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ScanActivity extends Activity {

	private String[] flagcontent;
	private boolean[] flagvalues;
	public static final String PERSFLAGS = "flags";
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.linearlay);
		flagcontent = this.getResources().getStringArray(R.array.nameOfFlags);
		flagvalues = getFlagesValues();
		listView = (ListView) findViewById(R.id.listView1);
		listView.setOnScrollListener(listViewListener);
		ArrayAdapter<String> adapter = new ListViewAdapter(this, flagcontent,
				this, flagvalues);
		listView.setAdapter(adapter);
	}

	private boolean[] getFlagesValues() {
		boolean[] flagstates = new boolean[flagcontent.length];
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		for (int i = 0; i < flagcontent.length; i++) {
			flagstates[i] = prefs.getBoolean(flagcontent[i], false);
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

	private OnScrollListener listViewListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView arg0, int arg1) {

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			System.out.println("   visible items  " + visibleItemCount);
			System.out.println(" all items: " + totalItemCount);
			System.out.println("all listitems " + view.getChildCount());
			System.out.println(" first visible item " + firstVisibleItem);
		}
	};

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
