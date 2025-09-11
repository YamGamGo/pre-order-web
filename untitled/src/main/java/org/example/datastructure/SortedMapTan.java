package org.example.datastructure;

import java.util.SortedMap;
import java.util.TreeMap;

public class SortedMapTan {
    public static void run() {
        SortedMap<String, Integer> map = new TreeMap<>();

        map.put("banana", 3);
        map.put("apple", 5);
        map.put("cherry", 2);
        map.put("date", 7);

        System.out.println("전체 Map (정렬됨): " + map);  // key 기준 오름차순

        System.out.println("첫 번째 키: " + map.firstKey());   // apple
        System.out.println("마지막 키: " + map.lastKey());     // date

        System.out.println("apple ~ cherry 사이: " + map.subMap("apple", "cherry"));  // apple만 포함

        System.out.println("cherry 이상: " + map.tailMap("cherry"));  // cherry, date
        System.out.println("cherry 미만: " + map.headMap("cherry"));  // apple, banana

        map.remove("banana");
        System.out.println("삭제 후: " + map);
    }
}

