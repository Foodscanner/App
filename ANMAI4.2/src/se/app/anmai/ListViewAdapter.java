package se.app.anmai;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * 
 * @author Sergej Gorr
 * @author Siourkul Zhooshbaev
 * 
 */
public class ListViewAdapter extends ArrayAdapter<String> {

	private Context context;
	private String[] flags;

	private static final String tag = "DEBUG_LISTVIEWADAPTER";

	public ListViewAdapter(Context context, String[] flags) {
		super(context, R.layout.fragment_main, flags);
		this.context = context;
		this.flags = flags;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.fragment_main, parent, false);

		}
		CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.flag_value);
		SharedPreferences sP = ((Activity) context)
				.getSharedPreferences(ScanActivity.flags_file,ScanActivity.MODE_PRIVATE);
		boolean state = sP.getBoolean(flags[position], false);

		// checkBox.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View checkBox) {
		// SharedPreferences.Editor editor = ((Activity)
		// context).getPreferences(ScanActivity.MODE_PRIVATE).edit();
		// Log.d(tag, "Flag " + flags[position] +" is changed manually to "+
		// ((CheckBox)checkBox).isChecked());
		// editor.putBoolean(flags[position],((CheckBox)checkBox).isChecked());
		// editor.commit();
		// }
		// });
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				SharedPreferences.Editor editor = ((Activity) context)
						.getSharedPreferences(ScanActivity.flags_file,ScanActivity.MODE_PRIVATE).edit();
				Log.d(tag, "Flag " + flags[position]
						+ " is changed manually to " + isChecked);
				editor.putBoolean(flags[position], isChecked);
				editor.commit();
			}
		});
		Log.d(tag, "State of " + flags[position] + " is " + state);
		checkBox.setChecked(state);
		TextView flag = (TextView) rowView.findViewById(R.id.article_title);
		flag.setText(flags[position]);
		return rowView;
	}

}
