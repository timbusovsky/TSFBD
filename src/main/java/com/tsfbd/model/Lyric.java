package com.tsfbd.model;

public class Lyric {

    private String lyric;
    private String song;
    private String album;
    private int songId;
    private int lineId;

    public Lyric(String lyric, String song, String album, int songId, int lineId) {
        this.lyric = lyric;
        this.song = song;
        this.album = album;
        this.songId = songId;
        this.lineId = lineId;
    }

    public Lyric(){}

    public String getLyric() {
        return this.lyric;
    }

    public String getSong() {
        return this.song;
    }

    public String getAlbum() {
        return this.album;
    }

    public int getSongId() {
        return this.songId;
    }

    public int getLineId() {
        return this.lineId;
    }

    public String setLyric(String lyric) {
        return this.lyric = lyric;
    }

    public String setSong(String song) {
        return this.song = song;
    }

    public String setAlbum(String album) {
        return this.album = album;
    }

    public int setSongId(int songId) {
        return this.songId = songId;
    }

    public int setLineId(int lineId) {
        return this.lineId = lineId;
    }

}
