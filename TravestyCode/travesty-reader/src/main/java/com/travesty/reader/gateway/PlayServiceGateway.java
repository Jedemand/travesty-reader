package com.travesty.reader.gateway;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.travesty.reader.domain.Book;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PlayServiceGateway {
    
    private RestTemplate restTemplate;
    private String playService;

    @Autowired
    public PlayServiceGateway(RestTemplate restTemplate,  @Value("${travesty.service}")String playService){
        this.restTemplate = restTemplate;
        this.playService = playService;
    }

    final String saveBook = "/book/";
    final String savePlay = "/play/";

    public Book sendBookToService(Book book){
        URI bookURI = UriComponentsBuilder.fromUriString(playService+saveBook)
                        .build()
                        .toUri();
        
        ResponseEntity<Book> response = null;
        try{
            response = restTemplate.postForEntity(bookURI, book, Book.class);
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return response.getBody();
    }




}
