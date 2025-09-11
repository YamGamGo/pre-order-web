package org.example.datastructure;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.Map;

public class NavigableMapTan {
    public static void run() {
        NavigableMap<String, Integer> map = new TreeMap<>();

        map.put("apple", 10);
        map.put("banana", 20);
        map.put("cherry", 30);
        map.put("date", 40);
        map.put("fig", 50);

        System.out.println("기본 Map (오름차순): " + map);

        System.out.println("lowerKey(\"cherry\"): " + map.lowerKey("cherry"));    // banana
        System.out.println("floorKey(\"cherry\"): " + map.floorKey("cherry"));    // cherry
        System.out.println("ceilingKey(\"cherry\"): " + map.ceilingKey("cherry"));// cherry
        System.out.println("higherKey(\"cherry\"): " + map.higherKey("cherry"));  // date

        System.out.println("--- 내림차순 Map ---");
        NavigableMap<String, Integer> descMap = map.descendingMap();
        for (Map.Entry<String, Integer> entry : descMap.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }

        System.out.println("--- subMap(\"banana\", true, \"fig\", false) ---");
        NavigableMap<String, Integer> sub = map.subMap("banana", true, "fig", false);
        System.out.println(sub);  // banana, cherry, date

        System.out.println("pollFirstEntry(): " + map.pollFirstEntry());  // apple 제거
        System.out.println("pollLastEntry(): " + map.pollLastEntry());    // fig 제거
        System.out.println("최종 Map: " + map);
    }
}

