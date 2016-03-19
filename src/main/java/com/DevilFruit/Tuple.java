package com.DevilFruit;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by susha on 3/18/2016.
 */
public class Tuple {
    List tuplet = null;

    public Tuple() {
        tuplet = new LinkedList<String>();
    }

    public void add(List<String> values) {
        tuplet.addAll(values);
    }

    public void add(String value) {
        tuplet.add(value);
    }

    public int size() {
        return tuplet.size();
    }

    public String get(int i) {
        return (String) tuplet.get(i);
    }

    public boolean equals(Tuple aTuple) {
        if (tuplet == null)
            return false;
        boolean isEqual = true;
        for (int i = 0; i < tuplet.size(); i++) {
            if (!tuplet.get(i).equals(aTuple.get(i))) {
                return false;
            }
        }
        return isEqual;
    }
}

