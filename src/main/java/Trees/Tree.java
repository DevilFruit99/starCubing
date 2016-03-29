package Trees;

import Tables.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by susha on 3/19/2016.
 */
public class Tree {
    public Node root;
    public CuboidTree.CuboidNode cuboidTreeCuboidNode;

    public Tree() {
        root = new Node();
        root.attribute = "+root";
    }

    public void createStarTree(Table compressedBaseTable) {
        String attr = "";
        String dimen = "";
        for (int i = 0; i < compressedBaseTable.table.size(); i++) {
            Node ptr = root;
            int rowCount = compressedBaseTable.table.get(i).getCount();
            root.count += rowCount;

            for (int j = 0; j < compressedBaseTable.key.size(); j++) {
                attr = compressedBaseTable.table.get(i).tuple.get(j);
                dimen = compressedBaseTable.key.get(j);
                Node temp = new Node();
                temp.attribute = attr;
                temp.dimension = dimen;
                int index = ptr.children.indexOf(temp);

                //ptr.count = rowCount + ptr.count;

                if (index < 0) {//add node to child

                    Node tempnode = new Node();
                    tempnode.attribute = attr;
                    tempnode.dimension = compressedBaseTable.key.get(j);
                    tempnode.count = rowCount;
                    tempnode.parent = ptr;
                    ptr.children.add(tempnode);

                    index = ptr.children.size() - 1;
                } else {
                    ptr.children.get(index).count += rowCount;
                }

                ptr = ptr.children.get(index);
            }
        }

    }

    public CuboidTree.CuboidNode getNextNullCTN() {
        for (CuboidTree.CuboidNode cn :
                cuboidTreeCuboidNode.children) {
            if (cn.starTree == null) {
                return cn;
            }
        }
        return null;
    }

    public static class Node {
        //public StarNode data;

        public String attribute;
        public String dimension;
        public int count;
        public Node parent;
        public List<Node> children = new ArrayList<Node>();

        public boolean equals(Object o) {

            return (attribute.equalsIgnoreCase(((Node) o).attribute)) && (dimension.equalsIgnoreCase(((Node) o).dimension));
        }

        public boolean isLeaf() {
            return children.size() == 0;
        }

        public boolean hasSibling() {
            if (this.parent == null)
                return false;
            return this.parent.children.size() - 1 > this.parent.children.indexOf(this);
        }

        public Node getNextSibling() {
            return this.parent.children.get(this.parent.children.indexOf(this) + 1);
        }

        public String toString() {
            return attribute + "/" + dimension;
        }
    }


}
