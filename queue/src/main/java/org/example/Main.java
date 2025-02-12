package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.UUID;
import java.util.zip.DeflaterInputStream;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        Properties properties = new Properties();

        try {
            Scanner scanner = new Scanner(new File("config"));
            while (scanner.hasNext()){
                Thread t = new Thread(new Producer(UUID.fromString(scanner.next())));
                t.start();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //new Producer(UUID.fromString(properties.getProperty("ID1")));
    }
}