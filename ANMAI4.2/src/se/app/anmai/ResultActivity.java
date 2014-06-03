package se.app.anmai;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
	
    TextView art_title;
    FrameLayout article_image;
    private static final String TESTURL = "3045320092066";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_articleinfo_layout);
		
		art_title=(TextView) findViewById(R.id.article_title);
		article_image = (FrameLayout) findViewById(R.id.article_image);
		Bundle dataIntent = getIntent().getExtras();
        String barcode = dataIntent.getString(ScanActivity.BARCODE_EXTRA);
        art_title.setText(barcode.toString());
        new ServerRequest(barcode.toString(), this);
	}
	
    public void setImage(Drawable art_image){
	  article_image.setBackground(art_image);
    }
    
    public void setTitle(String title){
    	art_title.setText(title);
    }
    
    public void fillArticleInfo(StandardExchangeArticle article){
    	((TextView) findViewById(R.id.article_title)).setText(article.getName());
    	((TextView) findViewById(R.id.description_content)).setText(article.getDescription());
        final View searchingLayout = findViewById(R.id.searching_layout);
        searchingLayout.setVisibility(View.GONE);
		final View v = findViewById(R.id.article_info_layout);
		v.setVisibility(View.VISIBLE);
    }

}
