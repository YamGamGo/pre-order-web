package org.example.datastructure;

import java.util.TreeSet;

public class TreeSetTan {
    public static void run() {
        TreeSet<String> set = new TreeSet<>();

        set.add("banana");
        set.add("apple");
        set.add("cherry");
        set.add("date");

        System.out.println("TreeSet (정렬됨): " + set); // [apple, banana, cherry, date]

        System.out.println("first(): " + set.first());     // apple
        System.out.println("last(): " + set.last());       // date
        System.out.println("contains(\"banana\"): " + set.contains("banana")); // true

        set.remove("banana");
        System.out.println("banana 제거 후: " + set);       // [apple, cherry, date]

        System.out.println("--- subSet(\"apple\", \"date\") ---");
        System.out.println(set.subSet("apple", "date"));   // [apple, cherry]

        System.out.println("--- descendingSet() ---");
        System.out.println(set.descendingSet());           // [date, cherry, apple]
    }
}

