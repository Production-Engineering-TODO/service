package ro.unibuc.hello;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.controller.UrlController;
import ro.unibuc.hello.data.Url;
import ro.unibuc.hello.repository.UrlRepository;

@SpringBootTest
class UrlControllerUnitTests {

    @Mock
    private Url Url;

    @InjectMocks
    private UrlController urlController;


    @Test
    void test_defaultShortend_returnsUrl(){
        String initialUrl = "www.google.com";

        String shortUrl = initialUrl.defaultShortenedUrl();

        Assertions.assertEquals("goo", shortUrl);
    }

    @Test
    void test_defaultShortend_onUrlAlreadyShort(){
        String initialUrl = "www.go.com";

        String shortUrl = initialUrl.defaultShortenedUrl();

        Assertions.assertEquals("go", shortUrl);
    }

    @Test
    void test_findUrlDomain_returnDomain(){
        String initialUrl = "www.forbes.uk";

        String domain = initialUrl.findUrlDomain();

        Assertions.assertEquals("uk", domain);
    }

    @Test
    void test_defaultShortend_onUrlAlreadyShort(){
        String initialUrl = "www.gov.ro";

        String matched = initialUrl.matchUrlDomain("ru");

        Assertions.assertEquals(false, shortUrl);
    }

}