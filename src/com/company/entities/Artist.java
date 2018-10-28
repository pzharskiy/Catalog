package com.company.entities;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
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

public class Artist {
    private String name;
    private List<Album> albums = new ArrayList<>();

    String getName() {
        return name;
    }

    Artist(File directoryItem) {
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

    void print() {
        System.out.println(name);
        for (Album album : albums
                ) {
            album.print();
        }
    }

    String printToFile() {
        String html = " <h3>\n " + name + " </h3>\n ";
        html += "<h4>\n ";
        for (Album album : albums
                ) {
            html += album.printToFile();
        }
        html += "</h4>\n";
        return html;
    }

    void addAlbum(File directoryItem) {
        //Если альбом существует, то запрашиваем альбом и добавляем в него песню
        if (albums.contains(new Album(directoryItem))) {
            getAlbum(directoryItem).addSong(directoryItem);
        }
        //Иначе добавляем новый альбом
        else {
            albums.add(new Album(directoryItem));
        }
    }

    Album getAlbum(File directoryItem) {
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
        //Проверка, существует ли альбом переданной песни (файла)
        for (Album album : albums
                ) {
            if (album.getTitle().equals(tag.getFirst(FieldKey.ALBUM))) {
                return album;
            }
        }
        return null;
    }

    public boolean equals(Object object) {
        if (object == this)
            return true;

     /* furniture ссылается на null */

        if (object == null)
            return false;

     /* Удостоверимся, что ссылки имеют тот же самый тип */

        if (!(getClass() == object.getClass()))
            return false;
        else {
            Artist tmp = (Artist) object;
            if (tmp.name.equals(this.name))
                return true;
            else
                return false;
        }
    }

    void findDublicates() {
        for (Album album : albums
                ) {
            album.findDublicates();
        }
    }
}