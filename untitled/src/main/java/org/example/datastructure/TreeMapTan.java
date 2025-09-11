package org.example.datastructure;

import java.util.Map;
import java.util.TreeMap;

public class TreeMapTan {
    public static void run() {
        TreeMap<String, Integer> map = new TreeMap<>();

        map.put("banana", 3);
        map.put("apple", 5);
        map.put("cherry", 2);
        map.put("date", 7);

        System.out.println("TreeMap 전체: " + map); // 자동 정렬됨

        System.out.println("첫 key: " + map.firstKey());  // apple
        System.out.println("마지막 key: " + map.lastKey()); // date

        System.out.println("cherry 값: " + map.get("cherry")); // 2

        map.remove("banana");

        System.out.println("--- 삭제 후 ---");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }

        System.out.println("--- subMap(\"apple\", \"date\") ---");
        Map<String, Integer> sub = map.subMap("apple", "date"); // apple 포함, date 미포함 해서 apple~date값을 불러옴
        System.out.println(sub); //아까 바나나는 삭제했으니깐 에플이랑, 체리가 반환됨
    }
}

