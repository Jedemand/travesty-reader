package com.travesty.reader.domain;

import java.util.List;

import javax.persistence.CascadeType;
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
public class Act {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer actId;

    String playAbbr;

    Integer actNumber;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Scene> scenes;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Tag> actTags;
}
