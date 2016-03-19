package com.DevilFruit;

import fileParser.CsVParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by susha on 3/18/2016.
 */
public class Table {
    List<TableTuple> table;
    Tuple key;//has one extra column count

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
        while (parser.hasNext()) {
            TableTuple newRow = new TableTuple(parser.next());
            table.add(newRow);
            newRow.increment();//make count to 1

        }

    }
}

