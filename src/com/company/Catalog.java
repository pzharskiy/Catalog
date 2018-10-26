package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Catalog {
    List<Artist> artists=new ArrayList<>();

    public Catalog(String directory) {
        File folder = new File(directory);
        File listOfFiles[]=folder.listFiles();
        obhod(directory, listOfFiles);
    }

    public void print()
    {
        if (artists.isEmpty())
        {
            System.out.println("List of artists is empty");
        }
        else {
            for (Artist artist : artists
                    ) {
                artist.print();
            }
        }
    }
    public void add(File directoryItem)
    {
        artists.add(new Artist(directoryItem));
    }

    private void obhod(String path,File listOfFiles[]) {
        for (File directoryItem : listOfFiles) {
            if (directoryItem.isDirectory()) {
                System.out.println("DIR= " + directoryItem.getName()+" "+directoryItem.length());
                File folder = new File(directoryItem.getPath());
                File list[]=folder.listFiles();
                obhod(directoryItem.getPath(), list);
            }
            if (directoryItem.isFile()) {
                System.out.println("File= " + directoryItem.getName());
                if (artists.contains(new Artist(directoryItem))) //переопределить equels для сравнения
                {
                    //добавляем к артисту его песни, если такой артист уже существует
                    //artists.get(new Artist(directoryItem)).addAlbum(adad);
                }
                else {
                    artists.add(new Artist(directoryItem));
                }

            }
        }
    }
}
