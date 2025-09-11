package org.example.practice;

import java.util.Stack;

public class DeliveryLv2 {

    public int solution(int[] order) {
        Stack<Integer> stack = new Stack<>();
        int current = 1;
        int index = 0;
        int loaded = 0;

        while (true) {
            if (index >= order.length) break;

            if (current == order[index]) {
                loaded++;
                current++;
                index++;
            } else if (!stack.isEmpty() && stack.peek() == order[index]) {
                stack.pop();
                loaded++;
                index++;
            } else if (current <= order.length) {
                stack.push(current++);
            } else {
                break;
            }
        }

        return loaded;
    }

    public static void main(String[] args) {
        DeliveryLv2 loader = new DeliveryLv2();

        System.out.println("결과1: " + loader.solution(new int[]{4, 3, 1, 2, 5})); // 2
        System.out.println("결과2: " + loader.solution(new int[]{5, 4, 3, 2, 1})); // 5
    }
}

