package Trees;

import Tables.Tuple;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import java.util.*;

/**
 * Created by susha on 3/19/2016.
 */
public class CuboidTree {
    public CuboidNode root;
    List<Tuple> listOfNodes;

    public CuboidTree(List<String> dimentions) {
        listOfNodes = new ArrayList<Tuple>();

        root = new CuboidNode();
        root.dimen = dimentions;
        root.level = 1;
        Collections.sort(root.dimen);

        //code to initialize the tree
        populateChildren(root, dimentions);

    }

    private void populateChildren(CuboidNode node, List<String> dimentions) {

        //populate the children of this node
        node.children = new ArrayList<CuboidNode>();

        ICombinatoricsVector<String> initialVector = Factory.createVector(dimentions);
        Generator<String> gen = Factory.createSimpleCombinationGenerator(initialVector, dimentions.size() - 1);

        if (dimentions.size() > 1) {
            for (ICombinatoricsVector<String> combination : gen) {
                Tuple temp = new Tuple(combination.getVector());
                if (!listOfNodes.contains(temp)) {
                    CuboidNode childNode = new CuboidNode();
                    childNode.level = node.level + 1;
                    childNode.dimen = combination.getVector();
                    childNode.parent = node;

                    node.children.add(childNode);
                    this.listOfNodes.add(new Tuple(combination.getVector()));
                    //System.out.println(combination.toString());
                }
            }
        }
        //call populateChildren method on its children
        if (dimentions.size() > 1) {
            for (CuboidNode childNode : node.children
                    ) {
                populateChildren(childNode, childNode.dimen);
            }
        }

        //set the shares dimensions
        if (node.children.size() == 0) {
            node.shares = node.dimen;
        } else {
            Set<String> uniqueSet = new HashSet<String>();
            for (int i = 0; i < node.children.size(); i++) {
                if (i == 0) {
                    uniqueSet.addAll(node.children.get(i).dimen);
                } else {
                    uniqueSet.retainAll(node.children.get(i).dimen);
                }
            }
            node.shares = new ArrayList<String>();
            node.shares.addAll(uniqueSet);
        }

    }


    public static class CuboidNode {
        //public StarNode data;
        public int level;
        public List<String> dimen;
        public List<String> shares;
        public List<String> aggregate;
        public CuboidNode parent;
        public Tree starTree;
        public List<CuboidNode> children;

        public boolean equals(Object o) {
            for (int i = 0; i < dimen.size(); i++) {
                if (!((CuboidNode) o).dimen.get(i).equalsIgnoreCase(dimen.get(i))) {
                    return false;
                }
            }
            return true;
        }

        public void removeChild(CuboidNode cc) {
            cc.starTree = null;

        }

        public String toString() {
            return (dimen.toString() + "/" + shares.toString());
        }
    }


}
