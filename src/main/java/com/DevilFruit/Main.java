package com.DevilFruit;

import fileParser.CsVParser;

import java.io.File;

public class Main {
    /**
     * @param args contains information about the data being processed
     *             arg[0] absolute file path
     *             arg[1] number of attributes. i.e number of dimen
     */
    public static void main(String[] args) {
        // Parse CSV file and add to Table
        File csvFile = new File(args[0]);
        System.out.print("Parsing " + csvFile.getName() + "...");
        CsVParser myParser = new CsVParser(csvFile.getAbsolutePath());
        Table baseCuboidTable = new Table();
        baseCuboidTable.populate(myParser);
        System.out.println("Done\n");

        //Perform star reduction

        //Get

      /*  BEGIN
        scan R twice, create star-table S and star-tree T;
        output count of T.root;
        call starcubing(T, T.root);
        END
        procedure starcubing(T, cnode)// cnode: current node
        {
            (1) for each non-null child C of T’s cuboid tree
                (2) insert or aggregate cnode to the corresponding
            position or node in C’s star-tree;
            (3) if (cnode.count ≥ min support) then {
            (4) if (cnode 6= root) then
                    (5) output cnode.count;
            (6) if (cnode is a leaf) then
                    (7) output cnode.count;
            (8) else { // initiate a new cuboid tree
                (9) create CC as a child of T’s cuboid tree;
                (10) let TC be CC’s star-tree;
                (11) TC.root’s count = cnode.count;
                (12) }
            (13) }
            (14) if (cnode is not a leaf) then
                (15) starcubing(T, cnode.first child);
            (16) if (CC is not null) then {
            (17) starcubing(TC,TC.root);
            (18) remove CC from T’s cuboid tree; }
            (19) if (cnode has sibling) then
                (20) starcubing(T, cnode.sibling);
            (21) remove T;
        }*/
    }
}
