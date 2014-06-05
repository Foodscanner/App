package com.example.internet_connection_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import deserializer.StandardExchangeArticle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
//Class Communicator{
//Product product;
//New Communicator(String barcode){
//StringBuffer barcodeXML = XMLParser.getXMLFor(barcode)
//BufferReader response = New ServerRequest(barcodeXML).getResponse();
//This.product = XMLParser.getProduct(response);
//}
//Public Product getProduct(){
//Return this.product;}

public class MainActivity extends Activity {
    TextView display;
    String []flags;
    
    private static final String tag = "FLAG";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		display=(TextView) findViewById(R.id.result_view);
		flags = getResources().getStringArray(R.array.flags);
		SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
		for(String s:flags)
		editor.putBoolean(s, true);
		editor.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void setRequest(View v){
		new ServerRequest("baijerde", this);
	
	}
	private class ResultFlagsAdapter extends ArrayAdapter<String> {

		private Context context;
		private String[] flags;

		private static final String tag = "ResultAdapter";

		public ResultFlagsAdapter(Context context, String[] flags) {
			super(context, R.layout.flag_layout, flags);
			this.context = context;
			this.flags = flags;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View rowView = convertView;
			if (rowView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				rowView = inflater.inflate(R.layout.flag_layout, parent, false);

			}

			TextView flag_title = (TextView) rowView.findViewById(R.id.flag_name);
			flag_title.setText(flags[position]);
			return rowView;
		}
	}
	
	private void fillFlags(StandardExchangeArticle article) {
		List<String> flags = getFlags(article);
		ListView view = (ListView) findViewById(R.id.flag_listView);
		String [] arrayOfFlags = new String[flags.size()];
		 flags.toArray(arrayOfFlags);
		 Log.d(tag, "Number of flags: "+ arrayOfFlags.length);
		view.setAdapter(new ResultFlagsAdapter(this, arrayOfFlags));
	}

	private List<String> getFlags(StandardExchangeArticle article) {
		HashMap<Integer, String> map = article.getFlags();
		Iterator it = article.getFlags().entrySet().iterator();
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		List<String> flagstates = new ArrayList<String>();

		while (it.hasNext()) {
			Map.Entry<Integer, String> pairs = (Map.Entry) it.next();
			if (prefs.getBoolean(pairs.getValue(), false)) {
				flagstates.add(pairs.getValue());
				Log.d(tag, "FLAG is available " + pairs.getValue()
						+ " is set to " + true);
			}

		}

		return flagstates;
	}
	public void fillArticleInfo(StandardExchangeArticle article) {
		
		fillFlags(article);
		

		
	}

}
