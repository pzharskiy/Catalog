package com.company;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Album {
    String title;
    List<File> songs=new ArrayList<>();

    public Album(File directoryItem) {
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
        this.title = tag.getFirst(FieldKey.ALBUM);
        songs.add(directoryItem);
    }

    public void print()
    {
        for (File song: songs
             ) {
                    AudioFile audioFile = null;
        try {
            audioFile = AudioFileIO.read(song);
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
        AudioHeader a= audioFile.getAudioHeader(); ///???
        System.out.println("artist: "+
        tag.getFirst(FieldKey.ARTIST) +
        "\nальбом: " + tag.getFirst(FieldKey.ALBUM)
                +"\nназвание: " +tag.getFirst(FieldKey.TITLE)
        +"\nсомментарии: " +tag.getFirst(FieldKey.COMMENT)
        +"\nгод: " +tag.getFirst(FieldKey.YEAR)
        +"\nтрек: " +tag.getFirst(FieldKey.TRACK)
        +"\nдиск: " +tag.getFirst(FieldKey.DISC_NO)
        +"\nкомпоизтор: " +tag.getFirst(FieldKey.COMPOSER)
        +"\nартист-сорт: " +tag.getFirst(FieldKey.ARTIST_SORT)
                +"\n"
        );
        }
    }

    public String getAlbum() {
        return title;
    }


}
