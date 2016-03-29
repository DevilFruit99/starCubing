package Tables;

import fileParser.CsVParser;

import java.util.*;

/**
 * Created by susha on 3/18/2016.
 */
public class Table {
    public List<TableTuple> table;
    public Tuple key;//has one extra column count

    public Table() {
        table = new ArrayList<TableTuple>();
        key = new Tuple();
    }

    public void add(Tuple tup) {
        int index = table.indexOf(tup);

        if (index > 0) {
            table.get(index).increment();
        } else {
            TableTuple newTT = new TableTuple(tup);
            table.add(newTT);
        }

    }

    public int getCount(Tuple tup) {
        return table.get(table.indexOf(tup)).getCount();
    }

    public void populate(CsVParser parser) {
        key.add(parser.next());

        List<String> oldOrder = new ArrayList<String>(key.tuplet);

        Collections.sort(key.tuplet);

        int[] sortHelper = new int[key.tuplet.size()];//stores the new location of this dimension
        for (String dimen : oldOrder
                ) {
            sortHelper[oldOrder.indexOf(dimen)] = key.tuplet.indexOf(dimen);
        }

        while (parser.hasNext()) {
            TableTuple newRow = new TableTuple(parser.next(), sortHelper);
            table.add(newRow);
            newRow.increment();//make count to 1

        }

    }

    public StarTable getStarTable(String dimension, int min_sup) {
        StarTable starTable = new StarTable(dimension);
        int index = key.indexOf(dimension);
        for (int i = 0; i < table.size(); i++) {
            starTable.insert(table.get(i).get(index));
        }

        starTable.validateStar(min_sup);
        return starTable;
    }

    public List<TableTuple> compress(List<StarTable> starTables) {
        //mark stars
        for (int i = 0; i < table.size(); i++) {
            for (int j = 0; j < key.size(); j++) {
                if (starTables.get(j).isStar(table.get(i).get(j))) {
                    table.get(i).star(j, key.get(j));

                }
            }
        }
        //remove duplicate rows
        Set<TableTuple> uniqueSet = new HashSet<TableTuple>(table);
        List<TableTuple> newtable = new ArrayList<TableTuple>(uniqueSet);
        for (TableTuple tt : uniqueSet) {
            newtable.get(newtable.indexOf(tt)).setCount(Collections.frequency(table, tt));

        }
        return newtable;
    }
}

