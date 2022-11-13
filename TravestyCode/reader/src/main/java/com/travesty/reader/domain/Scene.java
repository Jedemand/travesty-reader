package com.travesty.reader.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scene {
    //PlayAbbr	Act	Scene	Tags	Lines
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sceneId", unique = true, nullable = false)
    Integer sceneId;
    
    String sceneNumber;
    String playAbbr;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Line> lines;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Tag> sceneTags;



}
