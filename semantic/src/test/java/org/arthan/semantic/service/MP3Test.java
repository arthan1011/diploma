package org.arthan.semantic.service;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import org.junit.Test;

/**
 * Created by artur.shamsiev on 14.05.2015
 */
public class MP3Test {
    @Test
    public void testMP3() throws Exception {
        Mp3File mp3File = new Mp3File(getClass().getClassLoader().getResource("Kalimba.mp3").getFile());
        if (mp3File.hasId3v1Tag()) {
            ID3v2 tag = mp3File.getId3v2Tag();
            System.out.println(tag.getAlbum());
            System.out.println(tag.getArtist());
            System.out.println(tag.getGenreDescription());
            System.out.println(tag.getTitle());
        }

    }
}
