package com.travesty.reader;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;



@SpringBootApplication
public class ReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReaderApplication.class, args);
	}

	@Autowired
	ObjectMapper mapper;

	@PostConstruct
	public void setup() {mapper.registerModule(new JavaTimeModule());}



}
