package com.travesty.reader.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


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

    String workAbbr;
    
    String tobBody;
    Integer number;

    
}