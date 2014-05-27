package com.suiorkul.anmai;


import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
//import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.os.Build;

public class MainActivity extends Activity {
	
	String [] value = new String[]{"Laktose","Zucker","Milch","Schweifleich", "Alkohol","Vegetarisch",
									"Salz","Scharf","Bio","Eihaltig"};
    ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.linearlay);
		
		listView = (ListView) findViewById(R.id.listView1);
		
		ArrayAdapter<String> adapter = new ListViewAdapter(this,value);
		listView.setAdapter(adapter);
		
		
	}
	public void artiklescannen(View view){
		setContentView(R.layout.scanner_layout);
	}
	public void onClick(View view){
		IntentIntegrator integrator = new IntentIntegrator(this);
		integrator.initiateScan();
	}

	public void onActivityResult(int requestCode,int resultCode, Intent intent){
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if(scanResult != null){
		String barcode;
		String typ; 
		
		barcode=scanResult.getContents();
		typ = scanResult.getFormatName();
		
		EditText etBarcode = (EditText) findViewById(R.id.etBarcode);
		EditText etTyp = (EditText) findViewById(R.id.etTyp);
		
		etBarcode.setText(barcode.toString());
		etTyp.setText(typ.toString());
			
		}
	}

}
