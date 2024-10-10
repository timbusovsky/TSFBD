package com.tsfbd.controller;

import com.tsfbd.dao.LyricDao;
import com.tsfbd.model.Lyric;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

}
