package org.example.practice;

public class JoystickLV2 {
    public int solution(String name) {
        int answer = 0;
        int len = name.length();

        for (int i = 0; i < len; i++) {
            char c = name.charAt(i);
            int upDown = Math.min(c - 'A', 'Z' - c + 1);
            answer += upDown;
        }

        int move = len - 1;

        for (int i = 0; i < len; i++) {
            int next = i + 1;
            while (next < len && name.charAt(next) == 'A') {
                next++;
            }
            int backtrack = i * 2 + (len - next);
            int shortcut = i + 2 * (len - next);
            move = Math.min(move, Math.min(backtrack, shortcut));
        }

        answer += move;
        return answer;
    }

    public static void main(String[] args) {
        JoystickLV2 joystick = new JoystickLV2();
        System.out.println(joystick.solution("JEROEN")); // 56
        System.out.println(joystick.solution("JAN"));    // 23
        System.out.println(joystick.solution("AAA"));    // 0
        System.out.println(joystick.solution("ABABAAAABA")); // 예시 테스트
    }
}
