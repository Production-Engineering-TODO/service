package ro.unibuc.hello.controller;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
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

    @Autowired
    MeterRegistry metricsRegistry;

    @PostMapping("/")
    @Timed(value = "url.create.time", description = "Time taken to create url")
    @Counted(value = "url.create.count", description = "Times url was created")
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
                Url    existingShortUrl = urlRepository.findByShortUrl(payload.get("preferred"));
                if (existingShortUrl == null) {
                    shortUrl = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
                }
                else {
                    shortUrl = existingShortUrl.getShortUrl ();
                }
            }
            urlRepository.save(new Url(shortUrl, payload.get("url")));
        }

        return ("{url:\"" + shortUrl + "\"}");
    }

    @DeleteMapping ("/")
    @Timed(value = "url.delete.time", description = "Time taken to delete url")
    @Counted(value = "url.delete.count", description = "Times url was deleted")
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
    @Timed(value = "url.redirect.time", description = "Time taken to redirect url")
    @Counted(value = "url.redirect.count", description = "Times url was redirected")
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

