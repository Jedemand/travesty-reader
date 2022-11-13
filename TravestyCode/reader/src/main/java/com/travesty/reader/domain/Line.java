package com.travesty.reader.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer lineId;

    String playAbbr;
    String role;
    @Lob
    String lineBody;
    String lineNumber;

    
}
