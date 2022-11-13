package com.travesty.reader.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Literature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "litId", unique = true, nullable = false)
    Integer litId;
    
   
    String title;
    String subtitle;	
    Integer publicationYear;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Person author;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Dedication dedication;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Preface preface;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Prologue prologue;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Epilogue epilogue;
}
