package com.DevilFruit;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println("Hello World");
        File csvData = new File("/data/DressSales.csv");

        try {
            CSVParser parser = CSVParser.parse("/data/DressSales.csv", CSVFormat.EXCEL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
