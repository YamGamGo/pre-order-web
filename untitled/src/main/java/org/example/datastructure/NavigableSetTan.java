package org.example.datastructure;

import java.util.NavigableSet;
import java.util.TreeSet;

public class NavigableSetTan {
    public static void run() {
        NavigableSet<String> set = new TreeSet<>();

        set.add("apple");
        set.add("banana");
        set.add("cherry");
        set.add("date");
        set.add("fig");

        System.out.println("기본 오름차순 Set: " + set); // 정렬됨

        System.out.println("lower(\"cherry\"): " + set.lower("cherry"));   // banana
        System.out.println("floor(\"cherry\"): " + set.floor("cherry"));   // cherry
        System.out.println("ceiling(\"cherry\"): " + set.ceiling("cherry"));// cherry
        System.out.println("higher(\"cherry\"): " + set.higher("cherry")); // date

        System.out.println("--- 내림차순 Set ---");
        NavigableSet<String> desc = set.descendingSet();
        for (String s : desc) {
            System.out.print(s + " ");
        }
        System.out.println();

        System.out.println("pollFirst(): " + set.pollFirst()); // apple 제거
        System.out.println("pollLast(): " + set.pollLast());   // fig 제거

        System.out.println("남은 Set: " + set); // banana, cherry, date

        System.out.println("--- subSet(\"banana\", true, \"fig\", false) ---");
        NavigableSet<String> sub = set.subSet("banana", true, "fig", false);
        System.out.println(sub); // banana, cherry, date
    }
}

