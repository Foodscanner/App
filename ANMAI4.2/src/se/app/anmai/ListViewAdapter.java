package se.app.anmai;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class ListViewAdapter extends ArrayAdapter<String> {
	
	private final Context context;
	private final String[] flags; 
	private final ScanActivity list_activity;
	private final boolean [] flagstates;
	
	public ListViewAdapter(Context context, String[] flags, ScanActivity list_activity, boolean []flagstates){
		super(context, R.layout.fragment_main, flags);
		this.context=context;
		this.flags =flags;
		this.list_activity =list_activity;
		this.flagstates=flagstates;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View rowView =convertView;
		if(rowView ==null){
			LayoutInflater inflater = (LayoutInflater)context.
					getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView=inflater.inflate(R.layout.fragment_main, parent, false );
		}
		CheckBox checkBox = (CheckBox)rowView.findViewById(R.id.flag_value);
		
		if(flagstates[position])
			checkBox.setChecked(true);
		
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                boolean isChecked) {
            	SharedPreferences.Editor editor = list_activity.getPreferences(ScanActivity.MODE_PRIVATE).edit();
            	if(isChecked){
           	    editor.putBoolean(flags[position],isChecked); 
            	}else{
            		editor.remove(flags[position]);
            	}
            	editor.commit();
            }
          });
		
	    TextView flag = (TextView) rowView.findViewById(R.id.textView1);
	    flag.setText(flags[position]);
		return rowView;
	}
	
	
	
	
	

}
