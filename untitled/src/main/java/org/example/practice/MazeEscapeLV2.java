package org.example.practice;

import java.util.*;

public class MazeEscapeLV2 {
    public int solution(String[] maps) {
        int n = maps.length;
        int m = maps[0].length();

        Point start = null, lever = null, exit = null;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char ch = maps[i].charAt(j);
                if (ch == 'S') start = new Point(i, j);
                else if (ch == 'L') lever = new Point(i, j);
                else if (ch == 'E') exit = new Point(i, j);
            }
        }

        int toLever = bfs(maps, start, lever);
        if (toLever == -1) return -1;

        int toExit = bfs(maps, lever, exit);
        if (toExit == -1) return -1;

        return toLever + toExit;
    }

    private int bfs(String[] maps, Point start, Point end) {
        int n = maps.length;
        int m = maps[0].length();
        boolean[][] visited = new boolean[n][m];
        Queue<Point> queue = new LinkedList<>();
        queue.offer(new Point(start.x, start.y, 0));
        visited[start.x][start.y] = true;

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!queue.isEmpty()) {
            Point cur = queue.poll();
            if (cur.x == end.x && cur.y == end.y) return cur.dist;

            for (int dir = 0; dir < 4; dir++) {
                int nx = cur.x + dx[dir];
                int ny = cur.y + dy[dir];

                if (nx >= 0 && ny >= 0 && nx < n && ny < m &&
                        !visited[nx][ny] && maps[nx].charAt(ny) != 'X') {
                    visited[nx][ny] = true;
                    queue.offer(new Point(nx, ny, cur.dist + 1));
                }
            }
        }

        return -1;
    }

    static class Point {
        int x, y, dist;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
            this.dist = 0;
        }

        Point(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }

    public static void main(String[] args) {
        MazeEscapeLV2 maze = new MazeEscapeLV2();
        System.out.println(maze.solution(new String[]{"SOOOL", "XXXXO", "OOOOO", "OXXXX", "OOOOE"})); // 16
        System.out.println(maze.solution(new String[]{"LOOXS", "OOOOX", "OOOOO", "OOOOO", "EOOOO"})); // -1
    }
}

