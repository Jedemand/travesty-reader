package com.travesty.reader.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlayList {

    String title;
    Integer publication_year;
    String first;
    String last;
    
}
