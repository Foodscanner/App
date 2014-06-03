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
	private URI pictureURI;
	private HashMap<Integer, String> flags;

	// initialized flags to prevent null pointer exception
	public StandardExchangeArticle() {
		flags = new HashMap<Integer, String>();
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

	public URI getPictureURI() {
		return pictureURI;
	}

	public void setPictureURI(URI pictureURI) {
		this.pictureURI = pictureURI;
	}

	public HashMap<Integer, String> getFlags() {
		return flags;
	}

	public void clearFlags() {
		this.flags = new HashMap<Integer, String>();
		;
	}

	public void addFlag(Integer id, String flag) {
		flags.put(id, flag);
	}

	public void removeFlag(Integer id) {
		flags.remove(id);
	}

}
