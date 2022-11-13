package com.travesty.reader.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.travesty.reader.domain.Book;
import com.travesty.reader.domain.Play;
import com.travesty.reader.processes.TextToBook;
import com.travesty.reader.processes.TextToPlay;

@Controller
@RequestMapping("/reader")
public class readController {
    
    private TextToPlay textToPlay;
    private TextToBook textToBook;


    public readController(TextToPlay textToPlay, TextToBook textToBook) {
        this.textToPlay = textToPlay;
        this.textToBook = textToBook;
    }

    @GetMapping("/play/{fileName}")
    public ResponseEntity<Play> processPlayText(@PathVariable String fileName){
        return ResponseEntity.ok(textToPlay.readPlay(fileName));
    }

    @GetMapping("/book/{fileName}")
    public ResponseEntity<Book> processBookText(@PathVariable String fileName){
        return ResponseEntity.ok(textToBook.readBook(fileName));
    }
}
