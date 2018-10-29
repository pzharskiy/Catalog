package com.company.entities;

import com.company.entities.Artist;
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
import java.util.*;

public class Catalog {
    List<Artist> artists = new ArrayList<>();

    public Catalog(String directory) {
        File folder = new File(directory);
        File listOfFiles[] = folder.listFiles();
        treeTraversal(directory, listOfFiles); //Рекурсивный обход всего каталога
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

    public void printToFile() {

        String html = " <!DOCTYPE html>\n<html>\n<meta charset=\"utf-8\"\n<title></title>\n" +
                "<style>\np{\n padding-left: 20px;\n}\nh4{\n padding-left: 10px;\n}\n</style>\n" +
                "</head>\n<body>\n<div>\n";
        for (Artist artist : artists
                ) {
            html += artist.printToFile();
        }
        html += "</div>\n</body>\n</html>";
        File f = new File("D:\\test.html");
        try (BufferedWriter bw = new BufferedWriter((new FileWriter(f)))) {
            bw.write(html);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(File directoryItem) {
        artists.add(new Artist(directoryItem));
    }

    private void treeTraversal(String path, File listOfFiles[]) {
        for (File directoryItem : listOfFiles) {
            //Если пункт каталога другой каталог, то вызываем рекурсивную функцию
            if (directoryItem.isDirectory()) {
                //System.out.println("DIR= " + directoryItem.getName() + " " + directoryItem.length());
                File folder = new File(directoryItem.getPath());
                File list[] = folder.listFiles();
                treeTraversal(directoryItem.getPath(), list);
            }
            //Если пункт каталога файл, то проверяем расширение (необходимо mp3)
            if (directoryItem.isFile()) {
                //Проверка расширения файла
                if (!isMP3(directoryItem)) {
                    continue;
                }
                //Если такой артист уже есть в каталоге, то запрашиваем этого артиста и пробуем добавить альбом
                if (artists.contains(new Artist(directoryItem)))
                {
                    getArtist(directoryItem).addAlbum(directoryItem);

                }
                //Иначе добавляем нового артиста
                else {

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
        //Проверка, существует ли исполнитель переданной песни (файла)
        for (Artist artist : artists
                ) {
            if (artist.getName().equals(tag.getFirst(FieldKey.ARTIST))) {
                return artist;
            }
        }
        return null;
    }

    private boolean isMP3(File directoryItem) {
        String extension = "";
        int i = directoryItem.getName().lastIndexOf('.');
        if (i > 0) {
            extension = directoryItem.getName().substring(i + 1);
        }
        if (extension.equals("mp3")) {
            return true;
        } else return false;
    }

    public void findDublicates() {
        System.out.println("Дубликаты");
        if (artists.isEmpty()) {
            System.out.println("List of artists is empty");
        } else {
            for (Artist artist : artists
                    ) {
                artist.findDublicates();
            }
        }
    }

    public void findDublicatesWithoutCheckSum()
    {
        System.out.println("Дубликаты без контрольной суммой");
        if (artists.isEmpty()) {
            System.out.println("List of artists is empty");
        } else {
            Map<String, List<Song>> map=new HashMap<String, List<Song>>();
            map=createMap();
            printMap(map);
        }
    }

    private Map<String, List<Song>> createMap() {
        Map<String, List<Song>> map = new HashMap<String, List<Song>>();
        List<Song> list = new ArrayList<>();
        for (Artist artist : artists
                ) {
            list.addAll(artist.findDublicatesWithoutCheckSum());
        }
        for (Song song : list
                ) {
            List<Song> subList = new ArrayList<>();
            if (!map.containsKey(song.getTitle())) {
                for (Song songItem : list
                        ) {
                    if (songItem.getTitle().equals(song.getTitle()) && songItem.getAlbum().equals(song.getAlbum()) && songItem.getArtist().equals(song.getArtist()) ) {

                        subList.add(songItem);
                    }
                }
            } else continue;
            map.put(song.getTitle(), subList);
        }
        return map;
    }

    private void printMap(Map map)
    {
        Set<Map.Entry<String, List<Song>>> set = map.entrySet();
        List<Song> helpList;
        for (Map.Entry<String, List<Song>> me : set) {
            helpList = me.getValue();
            System.out.println(helpList.get(0).getTitle());
            if ( me.getValue().size()>1)
            {
                for (Song song : helpList
                        ) {
                    System.out.println(song.getPath() + " ");
                }
            }
        }
    }
}

