package com.DevilFruit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by susha on 3/18/2016.
 */
public class Table {
    List<TableTuple> table;
    Tuple key;//has one extra column count

    public Table(Tuple key) {
        this.key = key;
        this.key.add("Count");
        table = new ArrayList<TableTuple>();
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

    public int count(Tuple tup) {
        return table.get(table.indexOf(tup)).getCount();
    }
}

