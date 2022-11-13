package com.travesty.reader.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DramaticPersonae {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer dramaticPersonaeId;

    String playAbbr;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Role> roles;
}
