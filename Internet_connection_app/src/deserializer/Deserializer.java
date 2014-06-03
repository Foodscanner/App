package deserializer;

import com.thoughtworks.xstream.XStream;


public class Deserializer {
	public static StandardExchangeArticle deserializeStandardArticle(String xml){
		//System.out.println("xml: " + xml);
		XStream deserializer = new XStream();
		deserializer.alias("datatype.StandardExchangeArticle",StandardExchangeArticle.class);
		//System.out.println(deserializer.getClass() + " " + deserializer.toString());
		StandardExchangeArticle sea = null;
		try{
		sea = (StandardExchangeArticle)deserializer.fromXML(xml);
		}
		catch(Exception ex){
		//	System.out.println(ex);
		//	System.out.println("_-----------------------------------");
		}
		System.out.println("sea: " + sea);
		return sea;
	}
}
