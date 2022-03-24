package ro.unibuc.hello;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.controller.UrlController;
import ro.unibuc.hello.data.Url;
import ro.unibuc.hello.repository.UrlRepository;


@SpringBootTest
class UrlUnitTests {

    @Mock
    private UrlRepository urlRepository;

    @BeforeEach
    public void setUp() {
        urlRepository.deleteAll();
    }

    @Test
    void test_defaultShortend_returnsUrl(){
        Url initialUrl = new Url("goo" ,"www.google.com");

        String shortUrl = initialUrl.simpleShortenUrl();

        Assertions.assertEquals("goo", shortUrl);
    }

    @Test
    void test_defaultShortend_onUrlAlreadyShort(){
        Url initialUrl = new Url("go" ,"www.go.com");

        String shortUrl = initialUrl.simpleShortenUrl();

        Assertions.assertEquals("go", shortUrl);
    }

    @Test
    void test_findUrlDomain_returnDomain(){
        Url initialUrl = new Url("ggl", "www.forbes.uk");

        String domain = initialUrl.findUrlDomain();

        Assertions.assertEquals("uk", domain);
    }

    @Test
    void test_matchUrlDomain(){
        Url initialUrl = new Url("ggl", "www.gov.ro");

        Boolean matched = initialUrl.matchUrlDomain("ru");

        Assertions.assertEquals(false, matched);
    }

}