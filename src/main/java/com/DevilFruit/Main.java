package com.DevilFruit;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class Main {
    /**
     * @param args contains information about the data being processed
     *             arg[0] absolute file path
     *             arg[1] number of attributes. i.e number of dimen
     */
    public static void main(String[] args) {
        // write your code here
        System.out.println("Hello World");
        //TODO get file path from args value
       // File csvData = new File("C:\\Users\\susha\\IdeaProjects\\starCubing\\data\\DressSales.csv");
        File csvData = new File(args[0]);

        try {

            CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.DEFAULT);
            System.out.println(parser.getRecords().get(0).size());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
