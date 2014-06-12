package deserializer;

import java.net.URI;
import java.util.HashMap;

/**
 * Standard article format. Use when standard article information is required by
 * the App.
 * 
 * @author Gerald Melles
 * 
 */
public class StandardExchangeArticle {
private long ID;
private String name;
private String description;
private String pictureURL;
private HashMap<Integer, String> flags;
private HashMap<Integer,String> ingredients;

//initialized flags to prevent null pointer exception
public StandardExchangeArticle(){
 flags = new HashMap<Integer,String>();
 ingredients = new HashMap<Integer,String>();
}

public long getID() {
	return ID;
}
public void setID(long iD) {
	ID = iD;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getPictureURL() {
	return pictureURL;
}
public void setPictureURL(String pictureURL) {
	this.pictureURL = pictureURL;
}
public HashMap<Integer, String> getFlags() {
	return flags;
}

public HashMap<Integer, String> getIngredients() {
	return ingredients;
}
public void clearFlags() {
	this.flags = new HashMap<Integer,String>();;
}

public void addFlag(Integer id, String flag){
	flags.put(id, flag);
}

public void removeFlag(Integer id){
	flags.remove(id);
}

/**
 * @return the ingredients
 */
public void removeIngredient(Integer id) {
	ingredients.remove(id);
}

/**
 * @param ingredients the ingredients to set
 */
public void addIngredient(Integer id,String ingredient) {
	this.ingredients.put(id, ingredient);
}


}
