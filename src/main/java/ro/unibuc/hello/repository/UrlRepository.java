package ro.unibuc.hello.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.data.Url;

import java.util.List;

/**
 * No need to implement this interface.
 * Spring Data MongoDB automatically creates a class it implementing the interface when you run the application.
 */
@Repository
public interface UrlRepository extends MongoRepository<Url, String> {

    public Url findByShortUrl(String shortUrl);
    public Url findByLongUrl(String longUrl);
    public List<Url> findAllByLongUrl(String longUrl);


    public long count();

}