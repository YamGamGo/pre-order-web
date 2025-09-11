package org.example.practice;

import java.util.*;

public class MembershipDiscountLV2 {

    public int solution(String[] want, int[] number, String[] discount) {
        Map<String, Integer> wantMap = new HashMap<>();
        for (int i = 0; i < want.length; i++) {
            wantMap.put(want[i], number[i]);
        }

        int answer = 0;

        for (int i = 0; i <= discount.length - 10; i++) {
            Map<String, Integer> windowMap = new HashMap<>();

            for (int j = i; j < i + 10; j++) {
                windowMap.put(discount[j], windowMap.getOrDefault(discount[j], 0) + 1);
            }

            if (isMatch(wantMap, windowMap)) {
                answer++;
            }
        }

        return answer;
    }

    private boolean isMatch(Map<String, Integer> wantMap, Map<String, Integer> windowMap) {
        for (String item : wantMap.keySet()) {
            if (windowMap.getOrDefault(item, 0) < wantMap.get(item)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        MembershipDiscountLV2 md = new MembershipDiscountLV2();

        String[] want1 = {"banana", "apple", "rice", "pork", "pot"};
        int[] number1 = {3, 2, 2, 2, 1};
        String[] discount1 = {"chicken", "apple", "apple", "banana", "rice", "apple", "pork", "banana", "pork", "rice", "pot", "banana", "apple", "banana"};
        System.out.println(md.solution(want1, number1, discount1)); // 3

        String[] want2 = {"apple"};
        int[] number2 = {10};
        String[] discount2 = {"banana", "banana", "banana", "banana", "banana", "banana", "banana", "banana", "banana", "banana"};
        System.out.println(md.solution(want2, number2, discount2)); // 0
    }
}

