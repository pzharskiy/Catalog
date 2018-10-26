package com.company;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Artist {
    String name;
    List<Album> albums=new ArrayList<>();

    public Artist(File directoryItem) {
        AudioFile audioFile = null;
            try {
                audioFile = AudioFileIO.read(directoryItem);
            } catch (CannotReadException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TagException e) {
                e.printStackTrace();
            } catch (ReadOnlyFileException e) {
                e.printStackTrace();
            } catch (InvalidAudioFrameException e) {
                e.printStackTrace();
            }
            Tag tag = audioFile.getTag();
            this.name = tag.getFirst(FieldKey.ARTIST);
            albums.add(new Album(directoryItem));

    }

    public void print()
    {
        for (Album album: albums
             ) {
            album.print();
        }
    }

//    public void addAlbum()
//    {
//        if (albums.contains(наш новый альбом))
//        {
//            albums.getAlbum(наш новый альбом).addSong();
//        }
//        else
//        {
//
//        }
//    }
}
