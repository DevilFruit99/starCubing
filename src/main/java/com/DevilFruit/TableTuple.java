package com.DevilFruit;

import java.util.List;

/**
 * Created by susha on 3/19/2016.
 */
public class TableTuple extends Tuple {
    private int count;
    private Tuple tuple;

    public TableTuple(List<String> values) {
        tuple = new Tuple();
        tuple.add(values);
        count = 0;


    }

    public TableTuple(Tuple tup) {
        tuple = tup;
        count = 0;
    }

    public int increment() {
        count++;
        return count;
    }

    public int getCount() {
        return count;
    }

    public boolean equals(Tuple aTuple) {
        return tuple.equals(aTuple);
    }


}
