package com.travesty.reader.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@AllArgsConstructor
@NoArgsConstructor
public class Dedication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer dedicationId;

    String playAbbr;

    @Lob
    String dedicationBody;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Tag> dedicationTags;

}
