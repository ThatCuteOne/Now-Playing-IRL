package com.nowplayingirl.client.media;

import net.minecraft.util.Identifier;
import java.util.Objects;

public class MediaInfo {
    private String title;
    private String artist;
    private String album;
    private String source; // "Spotify", "Chrome", "VLC", etc.
    private Identifier albumArtTexture;
    private boolean isPlaying;
    private long timestamp;
    
    public MediaInfo() {
        this.timestamp = System.currentTimeMillis();
    }
    
    public MediaInfo(String title, String artist, String source) {
        this();
        this.title = title;
        this.artist = artist;
        this.source = source;
        this.isPlaying = true;
    }
    
    public boolean hasValidInfo() {
        return title != null && !title.isEmpty() && 
               artist != null && !artist.isEmpty();
    }
    
    // Getters et Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }
    
    public String getAlbum() { return album; }
    public void setAlbum(String album) { this.album = album; }
    
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    
    public Identifier getAlbumArtTexture() { return albumArtTexture; }
    public void setAlbumArtTexture(Identifier texture) { this.albumArtTexture = texture; }
    
    public boolean isPlaying() { return isPlaying; }
    public void setPlaying(boolean playing) { isPlaying = playing; }
    
    public long getTimestamp() { return timestamp; }
    
    public String getDisplayTitle() {
        if (title == null || title.length() <= 30) return title;
        return title.substring(0, 27) + "...";
    }
    
    public String getDisplayArtist() {
        if (artist == null || artist.length() <= 25) return artist;
        return artist.substring(0, 22) + "...";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediaInfo mediaInfo = (MediaInfo) o;
        return Objects.equals(title, mediaInfo.title) && 
               Objects.equals(artist, mediaInfo.artist);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(title, artist);
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s [%s]", artist, title, source);
    }
}