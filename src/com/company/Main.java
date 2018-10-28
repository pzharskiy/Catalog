package com.company;

import com.company.entities.Catalog;

public class Main {

    public static void main(String[] args) {
        // write your code here

        Catalog catalog = new Catalog("d:/catalogtest2/");
        //catalog.printToFile();
        catalog.print();
        catalog.findDublicates();
    }
}

