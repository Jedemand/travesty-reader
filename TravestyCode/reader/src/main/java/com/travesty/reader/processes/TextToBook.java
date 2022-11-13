package com.travesty.reader.processes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.travesty.reader.domain.Book;
import com.travesty.reader.domain.Chapter;
import com.travesty.reader.domain.Dedication;
import com.travesty.reader.domain.Paragraph;
import com.travesty.reader.domain.Person;
import com.travesty.reader.domain.Preface;
import com.travesty.reader.domain.Prologue;
import com.travesty.reader.domain.TableOfContents;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TextToBook {

    public Book readBook(String fileName){
        Book newBook = new Book();

        Path filename = Path.of("src/main/resources/text/books/"+fileName);

        String[] bookAbbr = fileName.split(".txt");
        newBook.setBookAbbr(bookAbbr[0]);

        try {
            String transcript = Files.readString(filename);
            String[] titleBreak = transcript.split("===TITLE===\n\n");
            newBook.setTitle(titleBreak[0]);
            transcript = titleBreak[1];

            String[] subTitleBreak = transcript.split("===SUBTITLE===\n\n");
            if(subTitleBreak.length < 2){
                log.info("No subtitle found for play: " + fileName);
            }else{
                newBook.setSubtitle(subTitleBreak[0]);
                transcript = subTitleBreak[1];
            }
            
            String[] authorBreak = transcript.split("===AUTHOR===\n\n");
            newBook.setAuthor(readAuthor(authorBreak[0].split(" ")));
            transcript = authorBreak[1];
            
            String[] publicationBreak = transcript.split("===PUBLICATION===\n\n");
            newBook.setPublicationYear(Integer.parseInt(publicationBreak[0]));
            transcript = publicationBreak[1];

            String[] publicationInfoBreak = transcript.split("===PUBLICATIONINFO===\n\n");
            if(publicationBreak.length < 2){
                log.info("No publication info found for play: " + fileName);
            }else{
                newBook.setPublishingInfo(publicationInfoBreak[0]);
                transcript = publicationInfoBreak[1];
            }

            String[] tableIntroBreak = transcript.split("===TABLEINTRO===\n");
            if(tableIntroBreak.length < 2){
                log.info("No table intro found for play: " + fileName);
            }else{
                newBook.setTableIntro(tableIntroBreak[0]);
                transcript = tableIntroBreak[1];
            }

            String[] tableOfContentBreak = transcript.split("===TABLEOFCONTENTS===\n\n");
            if(tableOfContentBreak.length < 2){
                log.info("No table of contents found for play: " + fileName);
            }else{
                newBook.setTableOfContents(readTable(tableOfContentBreak[0], newBook.getBookAbbr()));
                transcript = tableOfContentBreak[1];
            }

            String[] epigraphBreak = transcript.split("===EPIGRAPH===\n\n");
            if(epigraphBreak.length < 2){
                log.info("No epigraph found for play: " + fileName);
            }else{
                newBook.setEpigraph(epigraphBreak[0]);
                transcript = epigraphBreak[1];
            }

            String[] dedicationBreak = transcript.split("\n===DEDICATION===\n\n");
            if(dedicationBreak.length < 2){
                log.info("No dedication found for play: " + filename);
            }else{
                newBook.setDedication(readDedication(dedicationBreak[0], newBook.getBookAbbr()));
                transcript = dedicationBreak[1];
            }

            String[] prefaceBreak = transcript.split("\n===PREFACE===\n\n");
            if(prefaceBreak.length < 2){
                log.info("No preface found for play: " + fileName);
            }else{
                newBook.setPreface(readPreface(prefaceBreak[0], fileName));
                transcript = prefaceBreak[1];

            }

            String[] prologueBreak = transcript.split("===PROLOGUE===\n");
            if(prologueBreak.length < 2){
                log.info("No prologue found for play: " + fileName);
            }else{
                newBook.setPrologue(readPrologue(prologueBreak, fileName));
                transcript = prologueBreak[1];

            }

            String[] chapterBreak = transcript.split("\n\n===CHAPTER===\n\n");
            newBook.setChapters(readChapters(chapterBreak, newBook.getBookAbbr()));




            return newBook;
        
        }catch(Exception e){
            log.error(fileName, e);
        }

        return newBook;
    
    }

    public Person readAuthor(String[] authorData){
        Person author = new Person();
        author.setHonorific(authorData[0]);
        author.setFirst(authorData[1]);
        author.setLast(authorData[2]);
        if(author.getHonorific().equals("Mr.") || author.getHonorific().equals("Sir") || author.getHonorific().equals("Lord")){
            author.setPronoun("M");
        }else if(author.getHonorific().equals("Mrs.") || author.getHonorific().equals("Miss") || author.getHonorific().equals("Lady")){
            author.setPronoun("F");
        }else{
            author.setPronoun("N");
        }
        return author;
    }

    public List<TableOfContents> readTable(String tableData, String bookAbbr){
        List<TableOfContents> table = new ArrayList<>();

        String[] chapters = tableData.split("\n");
        for(int i = 0; i < chapters.length; i++){
            TableOfContents tableOfContents = new TableOfContents(null, bookAbbr, chapters[i], i+1);
            table.add(tableOfContents);
        }

        return table;
    }

    public Dedication readDedication(String dedicationData, String bookAbbr){
        Dedication dedication = new Dedication();
        dedication.setDedicationBody(dedicationData);
        
        dedication.setPlayAbbr(bookAbbr);

        return dedication;
    }

    public Preface readPreface(String prefaceData, String bookAbbr){
        Preface preface = new Preface();
        preface.setPreface(prefaceData);

        preface.setPlayAbbr(bookAbbr);
        return preface;
    }

    public Prologue readPrologue(String[] prologueData, String bookAbbr){
        Prologue prologue = new Prologue();
        String[] prologueObj = prologueData[0].split("===INTRO===");
        if(prologueObj.length < 2){
            prologue.setPrologue(prologueData[0]);
            log.info("No Intro for prologue in: " + bookAbbr);
        }else{
            prologue.setPrologue(prologueObj[1]);
            prologue.setIntro(prologueObj[0]);
        }

        prologue.setPlayAbbr(bookAbbr);
        return prologue;
    }

    public List<Chapter> readChapters(String[] chapterData, String bookAbbr){
        List<Chapter> chapterList = new ArrayList<>();

        for(int i =0; i < chapterData.length; i++){
            String[] paragraphBreak = chapterData[i].split("\n\n");
            Chapter chapter = new Chapter(null, bookAbbr, null, i+1, readParagraphs(paragraphBreak, bookAbbr), null);
            chapterList.add(chapter);
        }

        return chapterList;
    }

    public List<Paragraph> readParagraphs(String[] paragraphData, String bookAbbr){
        List<Paragraph> paragraphList = new ArrayList<>();

        for(int i=0; i<paragraphData.length; i++){
            String[] paragraphObj = paragraphData[i].split("=");
            if(paragraphObj.length < 2){
                Paragraph paragraph = new Paragraph(null, bookAbbr, paragraphObj[0], "prose", null);
                paragraphList.add(paragraph);
            }else{
                Paragraph paragraph = new Paragraph(null, bookAbbr, paragraphObj[0], paragraphObj[1], null);
            paragraphList.add(paragraph);
            }
            
        }

        return paragraphList;
    }
}