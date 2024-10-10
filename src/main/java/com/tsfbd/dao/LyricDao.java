package com.tsfbd.dao;

import com.tsfbd.model.Lyric;
import java.util.List;

public interface LyricDao {
    List<Lyric> getLyrics();
    List<Lyric> getLyricsBySongId(int songId);
}
