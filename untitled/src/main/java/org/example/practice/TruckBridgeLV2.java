package org.example.practice;

import java.util.LinkedList;
import java.util.Queue;

public class TruckBridgeLV2 {
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        Queue<Integer> bridge = new LinkedList<>();
        int time = 0;
        int totalWeight = 0;

        for (int i = 0; i < bridge_length; i++) {
            bridge.offer(0);
        }

        int idx = 0;

        while (idx < truck_weights.length) {
            time++;
            totalWeight -= bridge.poll();

            if (totalWeight + truck_weights[idx] <= weight) {
                bridge.offer(truck_weights[idx]);
                totalWeight += truck_weights[idx];
                idx++;
            } else {
                bridge.offer(0);
            }
        }

        return time + bridge_length;
    }

    public static void main(String[] args) {
        TruckBridgeLV2 tb = new TruckBridgeLV2();
        System.out.println(tb.solution(2, 10, new int[]{7, 4, 5, 6})); // 8
        System.out.println(tb.solution(100, 100, new int[]{10})); // 101
        System.out.println(tb.solution(100, 100, new int[]{10,10,10,10,10,10,10,10,10,10})); // 110
    }
}

