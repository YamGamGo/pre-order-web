package org.example.practice;

import java.util.*;

public class MandarinLV2 {

    public int solution(int k, int[] tangerine) {
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int size : tangerine) {
            countMap.put(size, countMap.getOrDefault(size, 0) + 1);
        }

        List<Integer> counts = new ArrayList<>(countMap.values());
        counts.sort(Collections.reverseOrder());

        int sum = 0;
        int kinds = 0;
        for (int count : counts) {
            sum += count;
            kinds++;
            if (sum >= k) break;
        }

        return kinds;
    }

    public static void main(String[] args) {
        MandarinLV2 mandarin = new MandarinLV2();

        System.out.println(mandarin.solution(6, new int[]{1, 3, 2, 5, 4, 5, 2, 3})); // 3
        System.out.println(mandarin.solution(4, new int[]{1, 3, 2, 5, 4, 5, 2, 3})); // 2
        System.out.println(mandarin.solution(2, new int[]{1, 1, 1, 1, 2, 2, 2, 3})); // 1
    }
}

