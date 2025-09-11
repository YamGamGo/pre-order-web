package org.example.practice;

import java.util.Arrays;
import java.util.Comparator;

public class BiggestNumberLV2 {
    public String solution(int[] numbers) {
        String[] nums = new String[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            nums[i] = String.valueOf(numbers[i]);
        }

        Arrays.sort(nums, (a, b) -> (b + a).compareTo(a + b));

        if (nums[0].equals("0")) return "0";

        StringBuilder answer = new StringBuilder();
        for (String num : nums) {
            answer.append(num);
        }

        return answer.toString();
    }

    public static void main(String[] args) {
        BiggestNumberLV2 bn = new BiggestNumberLV2();
        System.out.println(bn.solution(new int[]{6, 10, 2})); // "6210"
        System.out.println(bn.solution(new int[]{3, 30, 34, 5, 9})); // "9534330"
        System.out.println(bn.solution(new int[]{0, 0, 0})); // "0"
    }
}

