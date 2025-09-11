package org.example.practice;

import java.util.Stack;

public class NextGreaterNumberLV2 {

    public int[] solution(int[] numbers) {
        int n = numbers.length;
        int[] answer = new int[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() <= numbers[i]) {
                stack.pop();
            }

            if (stack.isEmpty()) {
                answer[i] = -1;
            } else {
                answer[i] = stack.peek();
            }

            stack.push(numbers[i]);
        }

        return answer;
    }

    public static void main(String[] args) {
        NextGreaterNumberLV2 sol = new NextGreaterNumberLV2();
        System.out.println(java.util.Arrays.toString(sol.solution(new int[]{2, 3, 3, 5}))); // [3, 5, 5, -1]
        System.out.println(java.util.Arrays.toString(sol.solution(new int[]{9, 1, 5, 3, 6, 2}))); // [-1, 5, 6, 6, -1, -1]
    }
}

