package com.company;

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

public class Catalog {
    List<Artist> artists = new ArrayList<>();

    public Catalog(String directory) {
        File folder = new File(directory);
        File listOfFiles[] = folder.listFiles();
        obhod(directory, listOfFiles);
    }

    public void print() {
        if (artists.isEmpty()) {
            System.out.println("List of artists is empty");
        } else {
            for (Artist artist : artists
                    ) {
                artist.print();
            }
        }
    }

    public void add(File directoryItem) {
        artists.add(new Artist(directoryItem));
    }

    private void obhod(String path, File listOfFiles[]) {
        for (File directoryItem : listOfFiles) {
            if (directoryItem.isDirectory()) {
                //System.out.println("DIR= " + directoryItem.getName() + " " + directoryItem.length());
                File folder = new File(directoryItem.getPath());
                File list[] = folder.listFiles();
                obhod(directoryItem.getPath(), list);
            }
            if (directoryItem.isFile()) {
                //System.out.println("File= " + directoryItem.getName());
                if (artists.contains(new Artist(directoryItem))) //переопределить equels для сравнения
                {
                    //System.out.println("Существующий " + directoryItem.getName() );
                    //добавляем к артисту его альбомы с песнями, если такой артист уже существует
                    getArtist(directoryItem).addAlbum(directoryItem);

                } else {
                    //System.out.println("Новый " + directoryItem.getName()+ " " +(artists.contains(new Artist(directoryItem))));
                    artists.add(new Artist(directoryItem));
                }

            }
        }
    }

    public Artist getArtist(File directoryItem) {

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

        for (Artist artist : artists
                ) {
            if (artist.getName().equals(tag.getFirst(FieldKey.ARTIST))) {
                return artist;
            }
        }
        return null;
    }
}