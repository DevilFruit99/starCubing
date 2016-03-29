package Tables;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by susha on 3/18/2016.
 */
public class Tuple {
    public List<String> tuplet = null;

    public Tuple() {
        tuplet = new LinkedList<String>();
    }

    public Tuple(List<String> strings) {
        tuplet = new ArrayList<String>(strings);
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
        return tuplet.get(i);
    }

    public boolean equals(Tuple aTuple) {
        if (tuplet == null)
            return false;
        if (tuplet.size() != aTuple.size())
            return false;
        boolean isEqual = true;
        for (int i = 0; i < tuplet.size(); i++) {
            if (!tuplet.get(i).contentEquals(aTuple.get(i))) {
                return false;
            }
        }
        return isEqual;
    }

    @Override
    public boolean equals(Object o) {
        Tuple anotherTuple = (Tuple) o;//new Tuple((List<String>)o);
        return this.equals(anotherTuple);
    }

    public String toString() {
        return tuplet.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.tuplet.toString() != null ? this.tuplet.toString().hashCode() : 0);
        return hash;
    }

    public int indexOf(String key) {
        return tuplet.indexOf(key);
    }
}

