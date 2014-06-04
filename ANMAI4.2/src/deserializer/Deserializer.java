package deserializer;

import com.thoughtworks.xstream.XStream;

public class Deserializer {
	public static StandardExchangeArticle deserializeStandardArticle(String xml) {
		XStream deserializer = new XStream();
		deserializer.alias("datatype.StandardExchangeArticle",
				StandardExchangeArticle.class);
		StandardExchangeArticle sea = null;
		try {
			sea = (StandardExchangeArticle) deserializer.fromXML(xml);
		} catch (Exception ex) {
			return sea;
		}
		System.out.println("sea: " + sea);
		return sea;
	}
}
