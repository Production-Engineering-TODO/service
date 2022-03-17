package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "url")
public class Url {

    @Id
    public String id;

	private String longUrl;
	
	private String shortUrl;
    
    public Url (String pShortUrl, String pLongUrl) {
        shortUrl = pShortUrl;
        longUrl  = pLongUrl;
    }
    
    public Url () {
    }

    public String getLongUrl () {
        return longUrl;
    }

    public String getShortUrl () {
        return shortUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    @Override
    public String toString() {
        return "Url{" +
                "longUrl='" + longUrl + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                '}';
    }
}