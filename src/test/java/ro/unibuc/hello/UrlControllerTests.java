package ro.unibuc.hello;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ro.unibuc.hello.controller.UrlController;
import ro.unibuc.hello.data.Url;
import ro.unibuc.hello.repository.UrlRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HelloApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
class UrlControllerTests {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UrlController urlController;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(urlController).build();
        objectMapper = new ObjectMapper();
        urlRepository.deleteAll();
    }

    @Test
    void createUrlPreferredTest () throws Exception {
        MvcResult result = mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\": \"www.google.ro\", \"preferred\": \"ggl\"}"))
                        .andExpect(status().isOk())
                        .andReturn();

        Assertions.assertEquals(result.getResponse().getContentAsString(), "{url:\"ggl\"}");
    }

    @Test
    void createUrlPreferredTwiceTest () throws Exception {
        MvcResult result = mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\": \"www.google.ro\", \"preferred\": \"ggl\"}"))
                        .andExpect(status().isOk())
                        .andReturn();

        Assertions.assertEquals(result.getResponse().getContentAsString(), "{url:\"ggl\"}");

        result = mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\": \"www.google.ro\", \"preferred\": \"somethingelse\"}"))
                        .andExpect(status().isOk())
                        .andReturn();

        Assertions.assertEquals(result.getResponse().getContentAsString(), "{url:\"ggl\"}");
    }

    @Test
    void createUrlRandomTest () throws Exception {
        MvcResult result = mockMvc.perform(post("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"url\": \"www.google.ro\"}"))
                                .andExpect(status().isOk())
                                .andReturn();

        Assertions.assertEquals(result.getResponse().getContentAsString().length(), "{url:\"aaaaaaaaaa\"}".length());
    }


    @Test
    void deleteUrlExistsTest () throws Exception {
        urlRepository.save(new Url ("ggl", "www.google.ro"));

        String response = mockMvc.perform(delete("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"url\":\"ggl\"}"))
                                .andExpect(status().isOk())
                                .andReturn().getResponse().getContentAsString ();

        Assertions.assertEquals(response , "{result:\"success\"}");
    }

    @Test
    void deleteUrlDoesNotExistTest () throws Exception {
        String response = mockMvc.perform(delete("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\":\"ggl\"}"))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString ();

        Assertions.assertEquals(response , "{result:\"failed\"}");
    }
    
    @Test
    void simpleRedirectTest () throws Exception {
        String expected = "https://www.google.ro";

        urlRepository.save(new Url ("ggl", "www.google.ro"));

        String response = mockMvc.perform(get("/ggl"))
                                .andReturn().getResponse().getRedirectedUrl();

        Assertions.assertEquals(expected, response);
    }
}
