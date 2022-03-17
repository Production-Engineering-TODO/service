package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.Url;
import ro.unibuc.hello.repository.InformationRepository;
import ro.unibuc.hello.repository.UrlRepository;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = InformationRepository.class)
public class HelloApplication implements CommandLineRunner {

	@Autowired
	private InformationRepository informationRepository;

	@Autowired
	private UrlRepository urlRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		informationRepository.deleteAll();
		informationRepository.save(new InformationEntity("Overview",
				"This is an example of using a data storage engine running separately from our applications server"));
	}

	@Override
	public void run(String... args) throws Exception {
		urlRepository.deleteAll();
		urlRepository.save(new Url("www.test.ro", "www.testest.ro"));
		urlRepository.save(new Url("www.TEST.ro", "www.TESTTEST.ro"));
	}

}
