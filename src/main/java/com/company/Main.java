package com.company;

import com.company.entities.Catalog;
import com.company.exceptions.AccessException;
import com.company.exceptions.EmptyFieldsOfFileExceptions;
import com.company.exceptions.NotExistingDirectoryException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Main {
    public static void main(String[] args) {
        // write your code here

        try {
            Catalog catalog = new Catalog("d:/catalogtest2/");
            //catalog.printToFile();
            catalog.print();
           // catalog.findDublicates();
            //catalog.findDublicatesWithoutCheckSum();
        }
        catch (AccessException | EmptyFieldsOfFileExceptions | NotExistingDirectoryException e)
        {
            e.printStackTrace();
        }
    }
}
