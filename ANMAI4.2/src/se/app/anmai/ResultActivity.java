package se.app.anmai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import deserializer.StandardExchangeArticle;
//Class Communicator{
//Product product;
//New Communicator(String barcode){
//StringBuffer barcodeXML = XMLParser.getXMLFor(barcode)
//BufferReader response = New ServerRequest(barcodeXML).getResponse();
//This.product = XMLParser.getProduct(response);
//}
//Public Product getProduct(){
//Return this.product;}

/**
 * 
 * @author Sergej Gorr
 * 
 */
public class ResultActivity extends Activity {

	FrameLayout article_image;
	private static final String TESTURL = "3045320092066";
	private static final String tag = "RESULT";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_articleinfo_layout);
		article_image = (FrameLayout) findViewById(R.id.article_image);
		Bundle dataIntent = getIntent().getExtras();
		String barcode = dataIntent.getString(ScanActivity.BARCODE_EXTRA);
		new ServerRequest(barcode.toString(), this);
//		new ServerRequest(TESTURL, this);

	}

	public void setImage(Drawable art_image) {
		article_image.setBackground(art_image);
	}


	public void fillArticleInfo(StandardExchangeArticle article) {
		final View v = findViewById(R.id.article_info_layout);
		if(article!=null){
		((TextView) findViewById(R.id.article_title))
				.setText(article.getName());
		((TextView) findViewById(R.id.description_content)).setText(article
				.getDescription());
		
		if(fillFlags(article)){
			v.setBackground(getResources().getDrawable(R.drawable.bg_allergies_yes));
			TextView allergies = (TextView) findViewById(R.id.allergies);
			allergies.setText(R.string.product_containt);
		}
		}
		final View searchingLayout = findViewById(R.id.searching_layout);
		searchingLayout.setVisibility(View.GONE);
		v.setVisibility(View.VISIBLE);		
	}

	private boolean fillFlags(StandardExchangeArticle article) {
		List<String> flags = getFlags(article);
		ListView view = (ListView) findViewById(R.id.resultflags_listview);
		String [] arrayOfFlags = new String[flags.size()];
		 flags.toArray(arrayOfFlags);
		 if(arrayOfFlags.length==0)
			 return false;
		view.setAdapter(new ResultFlagsAdapter(this, arrayOfFlags));
		return true;
	}

	private List<String> getFlags(StandardExchangeArticle article) {
		HashMap<Integer, String> map = article.getFlags();
		Iterator it = article.getFlags().entrySet().iterator();
		SharedPreferences prefs = getSharedPreferences(ScanActivity.flags_file,MODE_PRIVATE);
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
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
}