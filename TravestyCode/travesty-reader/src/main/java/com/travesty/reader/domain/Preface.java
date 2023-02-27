package com.travesty.reader.domain;
import java.util.List;

import javax.persistence.CascadeType;
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
@NoArgsConstructor
@AllArgsConstructor
public class Preface {
    //PlayAbbr	Preface	Tag
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer prefaceId;

    String workAbbr;

    @Lob
    String preface;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    List<Tag> prefaceTags;
    
}
