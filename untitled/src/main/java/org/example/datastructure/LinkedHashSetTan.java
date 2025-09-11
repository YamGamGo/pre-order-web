package org.example.datastructure;

import java.util.Iterator;
import java.util.LinkedHashSet;

public class LinkedHashSetTan {
    public static void run() {
        LinkedHashSet<String> set = new LinkedHashSet<>();

        set.add("apple");
        set.add("banana");
        set.add("cherry");
        set.add("apple"); // 중복 추가 시도 (무시됨)

        System.out.println("LinkedHashSet 내용: " + set);
        System.out.println("크기: " + set.size());  // 3

        System.out.println("banana 포함? " + set.contains("banana")); // true
        System.out.println("grape 포함? " + set.contains("grape"));   // false

        set.remove("banana");
        System.out.println("banana 삭제 후: " + set);

        System.out.println("Iterator로 순회:");
        Iterator<String> it = set.iterator();
        while(it.hasNext()) {
            String s = it.next();
            System.out.println(s);
        }

        //set.clear();
        System.out.println("모두 삭제 후 isEmpty? " + set.isEmpty());  // true , clear 하지 않으면 요소가 사과랑 체리가 있으니 false 반환
    }
}

