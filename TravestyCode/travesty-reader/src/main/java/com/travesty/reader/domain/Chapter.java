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
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer chapterId;

    String workAbbr;

    String chapterTitle;

    Integer chapterNumber;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Paragraph> paragraphs;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    List<Tag> chapterTags;
}
