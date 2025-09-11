package org.example.datastructure;

import java.util.Hashtable;
import java.util.Enumeration;

public class HashTableTan {
    public static void run() {
        Hashtable<String, Integer> table = new Hashtable<>();

        table.put("apple", 10);
        table.put("banana", 20);
        table.put("cherry", 30);

        System.out.println("전체 테이블: " + table);
        System.out.println("banana 값: " + table.get("banana"));

        table.remove("banana");
        System.out.println("삭제 후 테이블: " + table);

        System.out.println("apple 존재? " + table.containsKey("apple"));
        System.out.println("30 값 존재? " + table.containsValue(30));

        System.out.println("크기: " + table.size());
        System.out.println("비었는가? " + table.isEmpty());

        System.out.println("--- keys() ---");
        Enumeration<String> keys = table.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            System.out.println("Key: " + key + ", Value: " + table.get(key));
        }
    }
}

