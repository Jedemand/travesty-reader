package com.travesty.reader.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity(name = "play")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Play extends Literature{

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    DramaticPersonae dramaticPersonae;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Act> acts;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    List<Tag> playTags;



}