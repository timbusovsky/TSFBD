package com.tsfbd.dao;

import com.tsfbd.model.Lyric;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcLyricDao implements LyricDao {

    private JdbcTemplate jdbcTemplate;

    private final String sql = "SELECT lyrics.line, lyrics.line_id, song.song_name, album.album_name, song.song_id FROM lyrics " +
            "JOIN song ON song.song_id = lyrics.song_id " +
            "JOIN album ON album.album_id = song.album_id " +
            "ORDER BY lyrics.line_id ASC;";

    private final String sqlId = "SELECT lyrics.line, lyrics.line_id, song.song_name, album.album_name, song.song_id FROM lyrics " +
            "JOIN song ON song.song_id = lyrics.song_id " +
            "JOIN album ON album.album_id = song.album_id " +
            "WHERE song.song_id = ?;";

    public JdbcLyricDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Lyric> getLyrics() {

        List<Lyric> lyricList = new ArrayList<>();

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()) {
                Lyric lyric = mapRowToLyric(results);
                lyricList.add(lyric);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException(e);
        }
        return lyricList;
    }

    @Override
    public List<Lyric> getLyricsBySongId(int songId) {

        List<Lyric> lyricList = new ArrayList<>();

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sqlId, songId);
            while(results.next()) {
                Lyric lyric = mapRowToLyric(results);
                lyricList.add(lyric);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException(e);
        }
        return lyricList;
    }

    public Lyric mapRowToLyric(SqlRowSet row) {
        Lyric lyric = new Lyric();
        lyric.setLyric(row.getString("line"));
        lyric.setSong(row.getString("song_name"));
        lyric.setAlbum(row.getString("album_name"));
        lyric.setSongId(row.getInt("song_id"));
        lyric.setLineId(row.getInt("line_id"));
        return lyric;
    }

}
