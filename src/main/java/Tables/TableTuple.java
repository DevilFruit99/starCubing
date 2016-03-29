package Tables;

import java.util.List;

/**
 * Created by susha on 3/19/2016.
 */
public class TableTuple {
    public Tuple tuple;
    private int count;

    public TableTuple(List<String> values, int[] sortHelper) {
        tuple = new Tuple(values);
        for (int i = 0; i < sortHelper.length; i++) {
            if (i != sortHelper[i]) {
                tuple.tuplet.set(sortHelper[i], values.get(i));
            }
        }
        count = 0;
    }

    public TableTuple(Tuple tup) {
        tuple = tup;
        count = 0;
    }

    public void star(int i, String s) {
        tuple.tuplet.set(i, "*" + s);
    }

    public int increment() {
        count++;
        return count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String get(int index) {
        return tuple.get(index);
    }

    public boolean equals(Object o) {
        return tuple.equals(((TableTuple) o).tuple);
    }

    public void printHash() {
        System.out.println(tuple.toString() + " " + tuple.toString().hashCode());
    }

    public int hashCode() {
        return tuple.toString().hashCode();
    }
}
