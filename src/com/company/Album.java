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

    public void print() {
        System.out.println(title);
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
        AudioHeader a= audioFile.getAudioHeader(); ///??
        System.out.println("название: " +tag.getFirst(FieldKey.TITLE)
        +"\nгод: " +tag.getFirst(FieldKey.YEAR)
        +"\nпродолжительность: " +a.getTrackLength() +" c " +"\n");
        }
        //new SimpleDateFormat("hh:mm:ss").format(new Date(TimeUnit.SECONDS.toMillis(a.getTrackLength())))
    }

    public String printToFile()
    {
        String html = "" +title+"\n";
        html+=" <p>\n ";
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
            AudioHeader a= audioFile.getAudioHeader(); ///??
            html+=""+tag.getFirst(FieldKey.TITLE)+" "+a.getTrackLength() +" c " +"<a>"+song.getPath()+"</a> <br>";
        }
        html+="</p>\n";
        return html;
    }

    public String getTitle() {
        return title;
    }

    public void addSong(File directoryItem)
    {
        songs.add(directoryItem);
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
            Album tmp = (Album) object;
            if (tmp.title.equals(this.title))
                return true;
            else
                return false;
        }
    }
}
