package com.travesty.reader.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "book")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Book extends Literature{

    String publishingInfo;

    String tableIntro;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<TableOfContents> tableOfContents;

    String epigraph;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Chapter> chapters;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    List<Tag> bookTags;
    
}
