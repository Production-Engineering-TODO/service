package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ro.unibuc.hello.data.Url;
import ro.unibuc.hello.repository.UrlRepository;


@RestController
public class UrlController {

	@Autowired
	private UrlRepository urlRepository;

    @PostMapping("/create/{long_url}/{short_url}")
    public Url Create(@PathVariable("long_url") String long_url, @PathVariable("long_url") String short_url) {
        return urlRepository.save(new Url(short_url, long_url));
    }

    @DeleteMapping ("/delete/{long_url}")
    public Boolean Delete(@PathVariable("long_url") String long_url) {
        if(urlRepository.findByLongUrl(long_url) == null){
            return false;
        }
        urlRepository.delete(urlRepository.findByLongUrl(long_url));
        return true;
    }

    @GetMapping(value = "/redirect/{short_url}")
    public RedirectView Redirect (@PathVariable("short_url") String short_url) {
        Url shortUrl = urlRepository.findByShortUrl(short_url);
        return new RedirectView(shortUrl.getLongUrl());
    }
}

