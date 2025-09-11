package org.example.datastructure;

import java.util.Iterator;

public class IterableTan implements Iterable<String> {

    private final String[] items = {"apple", "banana", "cherry"};

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < items.length;
            }

            @Override
            public String next() {
                return items[index++];
            }
        };
    }

    public static void run() {
        IterableTan itTan = new IterableTan();    // 여기서 itTan. 찍으면 iterator() 나와야 함
        Iterator<String> it = itTan.iterator();   // 여기서 it. 찍으면 hasNext(), next() 나와야 함

        while(it.hasNext()) {
            String s = it.next();                  // s. 찍으면 toUpperCase(), length() 등 문자열 메서드 나와
            System.out.println(s.toUpperCase());
        }

        System.out.println("--- for-each 문 ---");
        for (String fruit : itTan) {
            System.out.println(fruit.charAt(0));  // fruit. 찍으면 charAt(), substring() 등 나와
        }
    }
}
