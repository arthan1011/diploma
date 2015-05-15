package org.arthan.semantic.model;

import com.google.common.base.Preconditions;
import org.arthan.semantic.util.FileUtils;

/**
 * Created by artur.shamsiev on 14.05.2015
 */
public class MP3File extends File {
    private String album;
    private String performer;
    private String musicTitle;
    private String genre;

    public MP3File(String path) {
        this.path = path;
        this.title = FileUtils.extractFileName(path);
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getMusicTitle() {
        return musicTitle;
    }

    public void setMusicTitle(String title) {
        this.musicTitle = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public static MP3File fromAbsPath(String absPath) {
        String path = FileUtils.toUnixPath(FileUtils.cutOffUserHome(absPath));
        return new MP3File(path);
    }

    public static MP3File fromUri(String uri) {
        Preconditions.checkArgument(
                uri.startsWith(URI),
                "MP3 file uri should start with " + URI
        );
        return new MP3File(uri.substring(URI.length()));
    }
}
