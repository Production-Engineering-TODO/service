package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

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

    public String simpleShortenUrl() {
        String[] splitUrl = this.longUrl.split("\\.");
        if (splitUrl[1].length()<3){
            String shorUrl = splitUrl[1] + "-" + splitUrl[2];
        }
        else{
            String shortUrl = splitUrl[1].substring(0,3);
        }

        return shortUrl;
    }

    public String findUrlDomain(){
        String[] splitUrl = this.longUrl.split("\\.");

        return splitUrl[2];
    }

    public boolean matchUrlDomain(String domain){
        String[] splitUrl = this.longUrl.split("\\.");

        return Objects.equals(domain, splitUrl[2]);
    }

}