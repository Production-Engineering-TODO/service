package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;


import ro.unibuc.hello.data.Url;
import ro.unibuc.hello.repository.UrlRepository;


@RestController
public class UrlController {

	@Autowired
	private UrlRepository urlRepository;

    @PostMapping(value = "/", consumes = "application/json")
    public Url create(@RequestBody String payload) {
        return urlRepository.save(new Url("test", "test"));
    }

    @DeleteMapping (value = "/", consumes = "application/json")
    public String destroy(@RequestBody String payload) {
        return payload;
    }

    @GetMapping(value = "/redirect/{short_url}")
    public RedirectView redirect (@PathVariable("short_url") String short_url) {
        Url shortUrl = urlRepository.findByShortUrl(short_url);
        return new RedirectView(shortUrl.getLongUrl());
    }
}

