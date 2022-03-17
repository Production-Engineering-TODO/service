package ro.unibuc.hello.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.data.InformationEntity;

import java.util.List;

/**
 * No need to implement this interface.
 * Spring Data MongoDB automatically creates a class it implementing the interface when you run the application.
 */
@Repository
public interface InformationRepository extends MongoRepository<InformationEntity, String> {

    public InformationEntity findByTitle(String title);
    public List<InformationEntity> findByDescription(String description);

}