package org.example.datastructure;

import java.util.SortedSet;
import java.util.TreeSet;

public class SortedSetTan {
    public static void run() {
        SortedSet<String> set = new TreeSet<>();

        set.add("banana");
        set.add("apple");
        set.add("cherry");
        set.add("date");

        System.out.println("정렬된 Set: " + set); // 자동 정렬됨

        System.out.println("가장 첫 요소: " + set.first());   // apple
        System.out.println("가장 마지막 요소: " + set.last()); // date

        System.out.println("--- headSet(\"cherry\") ---");
        System.out.println(set.headSet("cherry")); // apple, banana

        System.out.println("--- tailSet(\"cherry\") ---");
        System.out.println(set.tailSet("cherry")); // cherry, date

        System.out.println("--- subSet(\"banana\", \"date\") ---");
        System.out.println(set.subSet("banana", "date")); // banana, cherry
    }
}

