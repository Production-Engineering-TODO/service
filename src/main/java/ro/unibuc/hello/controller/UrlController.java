package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.unibuc.hello.data.Url;
import ro.unibuc.hello.repository.UrlRepository;

import java.util.HashMap;
import java.util.UUID;


@RestController
public class UrlController {

	@Autowired
	private UrlRepository urlRepository;

    @PostMapping("/")
    public String Create(@RequestBody HashMap<Object, String> payload) throws Exception {
        Url    existingUrl = urlRepository.findByLongUrl(payload.get("url"));
        String shortUrl;

        if (existingUrl != null)
            shortUrl = existingUrl.getShortUrl ();
        else {
            String preferred = payload.get("preferred");
            if (preferred != null) {
                shortUrl = preferred;
            } else {
                shortUrl = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
            }
            urlRepository.save(new Url(shortUrl, payload.get("url")));
        }

        return ("{url:\"" + shortUrl + "\"}");
    }

    @DeleteMapping ("/")
    public String Destroy(@RequestBody HashMap<Object, String> payload) throws Exception {
        Url existingUrl = urlRepository.findByShortUrl(payload.get ("url"));
        String result;

        if(existingUrl == null) {
            result = "failed";
        } else {
            urlRepository.delete(existingUrl);
            result = "success";
        }

        return ("{result:\"" + result + "\"}");
    }


    @RequestMapping(value = "/{shortUrl}", method = RequestMethod.GET)
    public ModelAndView Redirect(@PathVariable("shortUrl") String shortUrl) {
        Url existingUrl = urlRepository.findByShortUrl(shortUrl);

        String longUrl;
        if (existingUrl == null)
            longUrl = "/info";
        else
            longUrl = existingUrl.getLongUrl();

        return new ModelAndView("redirect:https://" + longUrl);
    }
}

