package com.travesty.reader.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableOfContents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer tocId;

    String bookAbbr;
    
    String tobBody;
    Integer number;

    
}