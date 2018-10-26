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
    private String name;
    private List<Album> albums=new ArrayList<>();

    public String getName() {
        return name;
    }

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
        System.out.println(name);
        for (Album album: albums
             ) {
            album.print();
        }
    }

    public String printToFile()
    {
        String html = " <h1>\n "+name+" </h1>\n ";
        html+=" <h2>\n ";
        for (Album album: albums
                ) {
            html+=album.printToFile();
        }
        html+="</h2>\n";
        return html;
    }

    public void addAlbum(File directoryItem)
    {
        if (albums.contains(new Album(directoryItem)))
        {
            //System.out.println("Существующий альбом");
            getAlbum(directoryItem).addSong(directoryItem);
        }
        else
        {
            //System.out.println("новый альбом");
            albums.add(new Album(directoryItem));
        }
    }

    public Album getAlbum(File directoryItem)
    {
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
}
