package org.example.practice;

import java.util.Arrays;

public class MissileInterceptorLV2 {
    public int solution(int[][] targets) {
        Arrays.sort(targets, (a, b) -> Integer.compare(a[1], b[1]));

        int count = 0;
        double lastIntercept = -1;

        for (int[] target : targets) {
            int start = target[0];
            int end = target[1];

            if (lastIntercept <= start) {
                count++;
                lastIntercept = end - 0.1;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        MissileInterceptorLV2 mi = new MissileInterceptorLV2();
        System.out.println(mi.solution(new int[][]{
                {4,5},{4,8},{10,14},{11,13},{5,12},{3,7},{1,4}
        })); // 3
    }
}

