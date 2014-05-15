package com.suiorkul.anmai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListViewAdapter extends ArrayAdapter<String> {
	
	private final Context context;
	private final String[] flags; 
	
	public ListViewAdapter(Context context, String[] flags){
		super(context, R.layout.fragment_main, flags);
		this.context=context;
		this.flags =flags;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView =convertView;
		if(rowView ==null){
			LayoutInflater inflater = (LayoutInflater)context.
					getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView=inflater.inflate(R.layout.fragment_main, parent, false );
		}
	    TextView flag = (TextView) rowView.findViewById(R.id.textView1);
	    flag.setText(flags[position] );
		return rowView;
	}
	
	
	
	
	

}
