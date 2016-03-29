package Tables;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by susha on 3/19/2016.
 */
public class StarTable {
    public String dimension;
    Map<String, Integer> starTable;

    public StarTable(String dimen) {
        starTable = new TreeMap<String, Integer>();
        this.dimension = dimen;
    }

    public void insert(String attribute) {
        if (!starTable.containsKey(attribute)) {
            starTable.put(attribute, 1);
        } else {
            int i = starTable.get(attribute);
            starTable.replace(attribute, ++i);
        }
    }

    public void validateStar(int min_sup) {//program goes through the and validates stars
        for (String key : starTable.keySet()) {
            if (starTable.get(key) < min_sup) {
                starTable.replace(key, -1);
            }
        }

    }

    public boolean isStar(String attribute) {
        return (starTable.get(attribute) == -1);
    }
}
