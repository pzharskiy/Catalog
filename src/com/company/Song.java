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

public class Song {
    private File song;
    private String title;
    private int year;
    private String duration;
    private String checkSum;



    public Song(File directoryItem) {
        this.song = directoryItem;

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
        AudioHeader audioHeader= audioFile.getAudioHeader(); ///?

        try {
            this.checkSum = (new MD5Checksum().getMD5Checksum(song.getPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.title = tag.getFirst(FieldKey.TITLE);
        this.year = Integer.valueOf(tag.getFirst(FieldKey.YEAR));
        this.duration = Time.getDurationString(audioHeader.getTrackLength());
    }
    public void print()
    {
        System.out.println("название: " +title
                +"\nгод: " +year
                +"\nпродолжительность: " +duration +"\ncheckSum: "+checkSum + "\n");
    }
    public String printToFile()
    {
        String html = null;
        return html+=""+title+" "+duration+" " +"<a href="+song.getPath()+">"+song.getPath()+"</a> <br>";
    }

    public File getSong() {
        return song;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getDuration() {
        return duration;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public String getPath()
    {
        return song.getPath();
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
            Song tmp = (Song) object;
            if (tmp.checkSum.equals(this.checkSum))
                return true;
            else
                return false;
        }
    }
}
