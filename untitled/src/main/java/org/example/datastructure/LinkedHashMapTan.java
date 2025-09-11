package org.example.datastructure;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapTan {
    public static void run() {
        Map<String, Integer> map = new LinkedHashMap<>();

        map.put("apple", 10);
        map.put("banana", 20);
        map.put("cherry", 30);
        map.put("date", 40);

        System.out.println("입력 순서 유지:");
        for (String key : map.keySet()) {
            System.out.println(key + " → " + map.get(key));
        }

        map.remove("banana");

        System.out.println("--- 삭제 후 ---");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("cherry 존재? " + map.containsKey("cherry"));  // true
        System.out.println("grape 존재? " + map.containsKey("grape"));    // false
    }
}

