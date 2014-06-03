package com.example.internet_connection_app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		display=(TextView) findViewById(R.id.result_view);
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

}
