package com.travesty.reader.processes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.travesty.reader.domain.Act;
import com.travesty.reader.domain.Dedication;
import com.travesty.reader.domain.DramaticPersonae;
import com.travesty.reader.domain.Line;
import com.travesty.reader.domain.Person;
import com.travesty.reader.domain.Play;
import com.travesty.reader.domain.Preface;
import com.travesty.reader.domain.Prologue;
import com.travesty.reader.domain.Role;
import com.travesty.reader.domain.Scene;
import com.travesty.reader.domain.Tag;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TextToPlay {

    public Play readPlay(String fileName){
        Play newPlay = new Play();

        Path filename = Path.of("src/main/resources/text/plays/"+fileName);
       
        try {
            String transcript = Files.readString(filename);
            String[] titleBreak = transcript.split("===TITLE===\n");
            newPlay.setTitle(titleBreak[0]);
            transcript = titleBreak[1];
            
            String[] subTitleBreak = transcript.split("===SUBTITLE===\n");
            if(subTitleBreak.length < 2){
                log.info("No subtitle found for play: " + fileName);
            }else{
                newPlay.setSubtitle(subTitleBreak[0]);
                transcript = subTitleBreak[1];
            }
            
            String[] authorBreak = transcript.split("===AUTHOR===\n");
            newPlay.setAuthor(readAuthor(authorBreak[0].split(" ")));
            transcript = authorBreak[1];
            
            String[] publicationBreak = transcript.split("===PUBLICATION===\n");
            newPlay.setPublicationYear(Integer.parseInt(publicationBreak[0]));
            transcript = publicationBreak[1];
            
            String[] dedicationBreak = transcript.split("===DEDICATION===\n");
            if(dedicationBreak.length < 2){
                log.info("No dedication found for play: " + filename);
            }else{
                newPlay.setDedication(readDedication(dedicationBreak, fileName));
                transcript = dedicationBreak[1];
            }

            String[] prefaceBreak = transcript.split("===PREFACE===\n");
            if(prefaceBreak.length < 2){
                log.info("No preface found for play: " + fileName);
            }else{
                newPlay.setPreface(readPreface(prefaceBreak, fileName));
                transcript = prefaceBreak[1];

            }

            String[] prologueBreak = transcript.split("===PROLOGUE===\n");
            if(prologueBreak.length < 2){
                log.info("No prologue found for play: " + fileName);
            }else{
                newPlay.setPrologue(readPrologue(prologueBreak, fileName));
                transcript = prologueBreak[1];

            }

            String[] dramaPersons = transcript.split("===DRAMATIC PERSONAE===\n");
            newPlay.setDramaticPersonae(readDramaticPersonae(dramaPersons, fileName));
            transcript = dramaPersons[1];

            String[] actBreak = transcript.split("\n===ACT===\n");
            newPlay.setActs(readActs(actBreak, fileName));
            transcript = actBreak[actBreak.length-1];

            
            
            
        } catch (IOException e) {
            log.error("Error reading play: " + e.getMessage() + ". File name: " + fileName);
            
        }
        List<Tag> tagList = new ArrayList<>();
        newPlay.setPlayTags(tagList);

        return newPlay;
    }

    public Person readAuthor(String[] authorData){
        Person author = new Person();
        author.setHonorific(authorData[0]);
        author.setFirst(authorData[1]);
        author.setLast(authorData[2]);
        if(author.getHonorific().equals("Mr") || author.getHonorific().equals("Sir") || author.getHonorific().equals("Lord")){
            author.setPronoun("M");
        }else if(author.getHonorific().equals("Mrs") || author.getHonorific().equals("Miss") || author.getHonorific().equals("Lady")){
            author.setPronoun("F");
        }else{
            author.setPronoun("N");
        }
        return author;
    }

    public Dedication readDedication(String[] dedicationData, String fileName){
        Dedication dedication = new Dedication();
        List<Tag> tagList = new ArrayList<>();
        dedication.setDedicationBody(dedicationData[0]);
        
        String[] playAbbr = fileName.split(".txt");
        dedication.setWorkAbbr(playAbbr[0]);
        dedication.setDedicationTags(tagList);
        return dedication;
    }

    public Preface readPreface(String[] prefaceData, String fileName){
        Preface preface = new Preface();
        List<Tag> tagList = new ArrayList<>();
        preface.setPreface(prefaceData[0]);

        String[] playAbbr = fileName.split(".txt");
        preface.setWorkAbbr(playAbbr[0]);
        preface.setPrefaceTags(tagList);
        return preface;
    }

    public Prologue readPrologue(String[] prologueData, String fileName){
        Prologue prologue = new Prologue();
        List<Tag> tagList = new ArrayList<>();
        String[] prologueObj = prologueData[0].split("===INTRO===");
        if(prologueObj.length < 2){
            prologue.setPrologue(prologueData[0]);
            log.info("No Intro for prologue in: " + fileName);
        }else{
            prologue.setPrologue(prologueObj[1]);
            prologue.setIntro(prologueObj[0]);
        }
       
        

        String[] playAbbr = fileName.split(".txt");
        prologue.setWorkAbbr(playAbbr[0]);
        prologue.setPrologueTags(tagList);
        return prologue;
    }

    public DramaticPersonae readDramaticPersonae(String[] dramaPersons, String fileName){
        DramaticPersonae dramaticPersonae = new DramaticPersonae();
        List<Role> roles = new ArrayList<>();
        String[] playAbbr = fileName.split(".txt");

        String[] allRoles = dramaPersons[0].split("\n");
        for(String part : allRoles){
            String[] roleData = part.split(":");
            Role role = new Role(null, playAbbr[0], roleData[1], roleData[0], roleData[2]);
            roles.add(role);
        }

        dramaticPersonae.setRoles(roles);
        dramaticPersonae.setWorkAbbr(playAbbr[0]);

        return dramaticPersonae;
    }

    public List<Act> readActs(String[] actData, String fileName){
        List<Act> actList = new ArrayList<>();
        String[] playAbbr = fileName.split(".txt");

        for(int i=0; i < actData.length; i++){
            List<Tag> tagList = new ArrayList<>();
            String[] sceneBreak = actData[i].split("\n===SCENE===\n");
            Act act = new Act(null, playAbbr[0], i+1, readScenes(sceneBreak, fileName), tagList);
            actList.add(act);

        }

        return actList;
    }

    public List<Scene> readScenes(String[] sceneData, String fileName){
        List<Scene> sceneList = new ArrayList<>();
        String[] playAbbr = fileName.split(".txt");

        for(int i=0; i < sceneData.length; i++){
            List<Tag> tagList = new ArrayList<>();
            String[] lineBreak = sceneData[i].split("\n");
            Scene scene = new Scene(null, String.valueOf(i+1), playAbbr[0], readLines(lineBreak, fileName), tagList);
            sceneList.add(scene);
        }
        return sceneList;
    }

    public List<Line> readLines(String[] lineData, String fileName){
        List<Line> lineList = new ArrayList<>();
        String[] playAbbr = fileName.split(".txt");

        for(int i=0; i< lineData.length; i++){
            List<Tag> tagList = new ArrayList<>();
            String[] lineobj=lineData[i].split(":===");
            Line line = new Line(null, playAbbr[0], lineobj[0], lineobj[1], String.valueOf(i+1), tagList);
            lineList.add(line);
        }
        return lineList;
    }
    
}
