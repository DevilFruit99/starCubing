package fileParser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by susha on 3/19/2016.
 */
public class CsVParser {
    Iterator<CSVRecord> iterator = null;
    private CSVParser parser = null;
     public CsVParser(String FilePath) {
        File csvData = new File(FilePath);

        try {
            parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.DEFAULT);
            iterator = parser.iterator();
            //System.out.println(parser.getRecords().get(0).size());
        } catch (IOException e) {
            System.out.println("No CSV file found in "+FilePath);
        }

    }
    public List<ArrayList<String>> getNext(){

        ArrayList<ArrayList<String>> recordsStr = null;

        List<CSVRecord> records = null;
        try {
            records = parser.getRecords();//gets complete list of
            recordsStr = new ArrayList<ArrayList<String>>();
        }catch (IOException e){
            System.out.println("Unable to parse line"+ parser.getCurrentLineNumber());
        }
        for(CSVRecord r:records){//r is one row in table
            Iterator<String> itr = r.iterator();
            ArrayList<String> oneRow = new ArrayList<String>();
            while(itr.hasNext()) {
                oneRow.add(itr.next());
            }
            recordsStr.add(oneRow);
        }
        return recordsStr;
    }
    public List<String> getHeader(){
        List<String> headers = new ArrayList<String>();
        Map<String,Integer> header = parser.getHeaderMap();
        for(String s:header.keySet()){
            headers.add(s);
        }

        return headers;
    }
    public long getCurrentLineNumber() {
        return parser.getCurrentLineNumber();
    }

    public Iterator<List<String>> iterator() {
        Iterator<List<String>> itr = new Iterator<List<String>>() {
            public boolean hasNext() {
                return parser.iterator().hasNext();
            }

            public List<String> next() {
                CSVRecord row = parser.iterator().next();
                ArrayList<String> oneRow = new ArrayList<String>();
                Iterator<String> rowItr = row.iterator();
                while(rowItr.hasNext()) {
                    oneRow.add(rowItr.next());
                }

                return oneRow;
            }
        };
        return itr;
    }

    public boolean hasNext(){
        return iterator.hasNext();
    }
    public List<String> next(){
        CSVRecord row = iterator.next();
        ArrayList<String> oneRow = new ArrayList<String>();
        Iterator<String> rowItr = row.iterator();
        while(rowItr.hasNext()) {
            oneRow.add(rowItr.next());
        }

        return oneRow;
    }
}
