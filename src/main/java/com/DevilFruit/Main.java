package com.DevilFruit;

import Tables.StarTable;
import Tables.Table;
import Trees.CuboidTree;
import Trees.Tree;
import fileParser.CsVParser;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static int min_sup;
    /**
     * @param args contains information about the data being processed
     * arg[0] absolute file path
     * arg[1] number of attributes. i.e number of dimen
     */
    private static boolean logResults = false;

    public static void main(String[] args) {
        // Parse CSV file and add to Table
        File csvFile = new File(args[0]);
        Instant start = Instant.now();
        System.out.print("Parsing " + csvFile.getName() + "...");
        CsVParser myParser = new CsVParser(csvFile.getAbsolutePath());
        Table baseCuboidTable = new Table();
        baseCuboidTable.populate(myParser);
        System.out.println("Done");


        //Perform star reduction to create a compressed base table
        //1. create a star table (hash table) for each dimension
        start = Instant.now();
        System.out.print("Creating a list of startables by dimension...");
        List<StarTable> starTables = new ArrayList<StarTable>();
        min_sup = Integer.parseInt(args[1]);

        List<String> orderedDimen = new ArrayList<String>(baseCuboidTable.key.tuplet);
        Collections.copy(orderedDimen, baseCuboidTable.key.tuplet);
        Collections.sort(orderedDimen);

        for (int i = 0; i < orderedDimen.size()/*baseCuboidTable.key.size()*/; i++) {
            StarTable starTable = new StarTable(orderedDimen.get(i) /*baseCuboidTable.key.get(i)*/);
            starTable = baseCuboidTable.getStarTable(starTable.dimension, min_sup);
            starTables.add(starTable);
        }
        System.out.println("Done");


        //2. Generate compressed star table
        System.out.print("Compressing base table...");
        //scan table again TODO clone baseCuboidTable instead
        CsVParser myParser2 = new CsVParser(csvFile.getAbsolutePath());
        Table compressedBaseTable = new Table();
        compressedBaseTable.populate(myParser2);

        compressedBaseTable.table = compressedBaseTable.compress(starTables);

        System.out.println("Done");

        //Create base Star Tree
        System.out.print("Creating base star tree...");
        Tree baseStarTree = new Tree();
        baseStarTree.createStarTree(compressedBaseTable);
        baseStarTree.root.count = baseCuboidTable.table.size();
        System.out.println("Done");

        //Create Cuboid Tree
        System.out.print("Creating cuboidTree...");
        CuboidTree cuboidTree = new CuboidTree(compressedBaseTable.key.tuplet);

        //linking the root node of cuboid tree to root of Base Star tree
        baseStarTree.cuboidTreeCuboidNode = cuboidTree.root;
        cuboidTree.root.starTree = baseStarTree;

        System.out.println("Done");
        //call starcubing

        System.out.print("Star Cubing...");
        starCubing(baseStarTree, baseStarTree.root);
        Instant end = Instant.now();
        System.out.println("Done: " + Duration.between(start, end));

    }

    /**
     * @param t     the current star tree
     * @param cnode current node in a star tree
     */
    private static void starCubing(Tree t, Tree.Node cnode) {
        //for each non-null child C of T's  cuboid tree insert of aggregate cnode to
        //corresponding position or node in C's star-tree
        for (CuboidTree.CuboidNode n : t.cuboidTreeCuboidNode.children) {
            //insert of aggregate cnode to corresponding position or node in C's star-tree
            if (n.starTree != null) {
                //check if value qualifies for insert in n
                if (n.dimen.contains(cnode.dimension)) {
                    //get Attribute Aggregate from cnode in dimensional order
                    List<String> AttrAggregate = new ArrayList<String>();
                    aggregateAttr(cnode, AttrAggregate);

                    //get Dimension Aggregate from cnode in dimensional order
                    List<String> DimenAggregate = new ArrayList<String>();
                    aggregateDimen(cnode, DimenAggregate);

                    //get counts of indices in dimensional order
                    List<Integer> counts = new ArrayList<Integer>();
                    aggregateCount(cnode, counts);
                    //use AttrAggregate to traverse and insert
                    if (n.starTree == null) {//create a new star tree
                        n.starTree = new Tree();
                        n.starTree.cuboidTreeCuboidNode = n;
                    }
                    Tree.Node ptr = n.starTree.root;
                    //
                    for (int i = 0; i < AttrAggregate.size(); i++) {
                        //check dimension
                        if (n.dimen.contains(DimenAggregate.get(i))) {
                            //check attribute and if cuboid node aggregate doesn't contain
                            //then add as a node
                            if (!n.aggregate.contains(AttrAggregate.get(i))) {
                                Tree.Node newNode = new Tree.Node();
                                newNode.attribute = AttrAggregate.get(i);
                                newNode.dimension = DimenAggregate.get(i);
                                newNode.count = counts.get(i);
                                int index = ptr.children.indexOf(newNode);
                                if (index < 0) {//adding the new pointer
                                    ptr.children.add(newNode);
                                    newNode.parent = ptr;
                                    ptr = newNode;
                                } else {
                                    ptr = ptr.children.get(index);
                                }
                            }
                        }
                    }
                }
            }
        }
        CuboidTree.CuboidNode CC = null;
        Tree TC = null;

        if (cnode.count >= min_sup) {
            if (!cnode.attribute.equalsIgnoreCase("+root") && logResults) {
                // output cnode.count
                List<String> attrAggregate = new ArrayList<String>();
                aggregateAttr(cnode, attrAggregate);
                System.out.println(attrAggregate + "/" + cnode.count + "/" + t.cuboidTreeCuboidNode.aggregate);

            } else if (cnode.isLeaf() && logResults) {
                // output cnode.count
                List<String> attrAggregate = new ArrayList<String>();
                aggregateAttr(cnode, attrAggregate);
                System.out.println(attrAggregate + "/" + cnode.count + t.cuboidTreeCuboidNode.aggregate);

            }
            if (!cnode.isLeaf()) {//initiate a new cuboid tree,
                // create CC as a child of T's cuboid tree
                //(see which of current cuboid node's children have no star tree)
                //t.cuboidTreeCuboidNode.children.add(CC);//dont know why
                CC = t.getNextNullCTN();//CTN is cuboid tree node
                //TODO populate aggregate in CC if its not root
                if (CC != null) {
                    List<String> aggregateStr = new ArrayList<String>();
                    aggregateAttr(cnode, aggregateStr);
                    List<String> aggregateDimStr = new ArrayList<String>();
                    aggregateDimen(cnode, aggregateDimStr);
                    CC.aggregate = new LinkedList<String>();
                    for (int i = 0; i < CC.dimen.size(); i++) {
                        if (aggregateDimStr.contains(CC.dimen.get(i))) {
                            CC.aggregate.add(aggregateStr.get(i));
                        } else {
                            CC.aggregate.add(CC.dimen.get(i));
                        }
                    }

                    //let TC be CC's star tree
                    TC = new Tree();
                    TC.root.count = cnode.count;
                    CC.starTree = TC;
                    TC.cuboidTreeCuboidNode = CC;
                }
            }
        }
        if (!cnode.isLeaf()) {
            starCubing(t, cnode.children.get(0));//get first Child
        }
        if (CC != null) {
            starCubing(TC, TC.root);
            t.cuboidTreeCuboidNode.removeChild(CC);
        }
        if (cnode.hasSibling()) {
            starCubing(t, cnode.getNextSibling());
        }
    }

    private static void aggregateCount(Tree.Node cnode, List<Integer> counts) {
        if (cnode.parent == null) {
            return;
        }
        aggregateCount(cnode.parent, counts);
        counts.add(cnode.count);
    }

    private static void aggregateDimen(Tree.Node cnode, List<String> dimenAggregate) {
        if (cnode.parent == null) {
            return;
        }
        aggregateAttr(cnode.parent, dimenAggregate);
        dimenAggregate.add(cnode.dimension);
    }

    private static void aggregateAttr(Tree.Node cnode, List<String> aggregate) {

        if (cnode.parent == null) {
            return;
        }
        aggregateAttr(cnode.parent, aggregate);
        aggregate.add(cnode.attribute);

    }
}
