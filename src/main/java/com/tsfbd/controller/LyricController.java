package com.tsfbd.controller;

import com.tsfbd.dao.LyricDao;
import com.tsfbd.model.Lyric;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/lyrics")
public class LyricController {

    private LyricDao lyricDao;

    public LyricController(LyricDao lyricDao) {
        this.lyricDao = lyricDao;
    }


    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Lyric> getLyrics() {
        return lyricDao.getLyrics();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Lyric> getLyricsById(@PathVariable int id) {
        return lyricDao.getLyricsBySongId(id);
    }

    @RequestMapping ("/api/{input}")
    private List<Lyric> search(@PathVariable String input) {

        List<Lyric> allLines = lyricDao.getLyrics();
        List<Lyric> filteredLyrics = new ArrayList<>();
        List<Lyric> specificLyricLines = new ArrayList<>();
        StringBuilder fullyLyricsForSong = new StringBuilder();
        StringBuilder fullLyricsForSong = new StringBuilder();
        int currentSongId = 1;
        String acronym = "";
        Map<Integer, String> indexesAndAcronyms = new HashMap<>();
        String specificLyrics = "";

        for(Lyric lyric : allLines) {
            if(lyric.getSongId() == currentSongId) {
                fullyLyricsForSong.append(lyric.getLyric()).append(" ");
            } else {
                acronym = getAcronym(fullyLyricsForSong.toString());
                String[] separatedLyrics = getLyricArray(fullyLyricsForSong.toString());
                if(acronym.toLowerCase().contains(input.toLowerCase())) {
                    indexesAndAcronyms = getIndexesAndAcronyms(input, acronym);
                    specificLyricLines = lyricDao.getLyricsBySongId(currentSongId);
                    specificLyrics = getSpecificLyrics(indexesAndAcronyms, separatedLyrics, input);
                    filteredLyrics = getLinesFromSpecificLyricsString(specificLyricLines, specificLyrics);
                }
                currentSongId++;
                fullyLyricsForSong = new StringBuilder(lyric.getLyric() + " ");
            }
        }

        return removeDuplicates(filteredLyrics);

    }



    private String[] getLyricArray(String lyricLine) {
        return lyricLine.split(" ");
    }

    private String getAcronym(String lyricLine) {

        String[] parts = getLyricArray(lyricLine);
        StringBuilder acronym = new StringBuilder();

        for (String s : parts) {
            if (s.length() >= 2) {
                if (!(s.charAt(0) >= 'a' &&s.charAt(0) <= 'z' || s.charAt(0) >= 'A' &&s.charAt(0) <= 'Z')) {
                    acronym.append(s.charAt(1));
                } else {
                    acronym.append(s.charAt(0));
                }
            }
            if(s.length() == 1) {
                acronym.append(s.charAt(0));
            }
        }

        return acronym.toString().toUpperCase();

    }

    private String getSpecificLyrics(Map<Integer, String> indexesAndAcronyms, String[] splitLyrics, String acronym) {

        int index = 0;

        for(Map.Entry<Integer, String> entry : indexesAndAcronyms.entrySet()) {
            if(entry.getValue().equalsIgnoreCase(acronym)) {
                index = entry.getKey();
            }
        }

        return getSpecificLyricsByIndex(index, splitLyrics, acronym);

    }

    private String getSpecificLyricsByIndex(int index, String[] splitSongLyrics, String acronym) {

        StringBuilder specificLyrics = new StringBuilder();

        for(int i = index; i < index + acronym.length(); i++) {
            specificLyrics.append(splitSongLyrics[i]).append(" ");
        }

        return specificLyrics.toString();

    }

    private List<Lyric> getLinesFromSpecificLyricsString(List<Lyric> songLyrics, String specificLyrics) {

        List<Lyric> filteredLyrics = new ArrayList<>();

        for(Lyric lyric : songLyrics) {
            if(specificLyrics.contains(lyric.getLyric())) {
                filteredLyrics.add(lyric);
            }
        }
        return filteredLyrics;
    }

    private List<Lyric> removeDuplicates(List<Lyric> lyrics) {

        List<Lyric> filteredLyrics = new ArrayList<>();
        filteredLyrics.add(lyrics.get(0));
        String line = lyrics.get(0).getLyric();

        for(Lyric lyric : lyrics) {
            if(!lyric.getLyric().equals(line)) {
                filteredLyrics.add(lyric);
                line = lyric.getLyric();
            }
        }

        return filteredLyrics;

    }

    private  Map<Integer, String> getIndexesAndAcronyms(String input, String acronym) {

        int index = 0;
        int endIndex = input.length();
        String substring = acronym.substring(0, endIndex);
        Map<Integer, String> indexesAndAcronyms = new HashMap<>();

        for(int i = 0; i < acronym.length() - input.length() - 1; i++) {
            indexesAndAcronyms.put(i, substring);
            index++;
            endIndex++;
            substring = acronym.substring(index, endIndex);
        }

        return indexesAndAcronyms;

    }


}
