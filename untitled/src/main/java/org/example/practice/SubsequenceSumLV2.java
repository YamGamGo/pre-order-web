package org.example.practice;

public class SubsequenceSumLV2 {

    public int[] solution(int[] sequence, int k) {
        int left = 0, right = 0, sum = sequence[0];
        int minLen = Integer.MAX_VALUE;
        int start = 0, end = 0;

        while (right < sequence.length) {
            if (sum == k) {
                if ((right - left + 1) < minLen) {
                    minLen = right - left + 1;
                    start = left;
                    end = right;
                }
                sum -= sequence[left++];
            } else if (sum < k) {
                if (++right < sequence.length) {
                    sum += sequence[right];
                }
            } else {
                sum -= sequence[left++];
            }
        }

        return new int[]{start, end};
    }

    public static void main(String[] args) {
        SubsequenceSumLV2 solver = new SubsequenceSumLV2();

        int[] result1 = solver.solution(new int[]{1, 2, 3, 4, 5}, 7); // [2, 3]
        int[] result2 = solver.solution(new int[]{1, 1, 1, 2, 3, 4, 5}, 5); // [6, 6]
        int[] result3 = solver.solution(new int[]{2, 2, 2, 2, 2}, 6); // [0, 2]

        System.out.println("결과1: [" + result1[0] + ", " + result1[1] + "]");
        System.out.println("결과2: [" + result2[0] + ", " + result2[1] + "]");
        System.out.println("결과3: [" + result3[0] + ", " + result3[1] + "]");
    }
}

