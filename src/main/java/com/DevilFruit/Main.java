package com.DevilFruit;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println("Hello World");
        //TODO get file path from args value
        File csvData = new File("C:\\Users\\susha\\IdeaProjects\\starCubing\\data");

        try {

            CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.EXCEL);
            System.out.println(parser.getRecordNumber());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
