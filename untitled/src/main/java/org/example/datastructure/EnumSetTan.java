package org.example.datastructure;

import java.util.EnumSet;

public class EnumSetTan {

    enum Day {
        MON, TUE, WED, THU, FRI, SAT, SUN
    }

    public static void run() {
        EnumSet<Day> weekdays = EnumSet.range(Day.MON, Day.FRI);
        EnumSet<Day> weekend = EnumSet.of(Day.SAT, Day.SUN);
        EnumSet<Day> allDays = EnumSet.allOf(Day.class);
        EnumSet<Day> none = EnumSet.noneOf(Day.class);

        System.out.println("평일: " + weekdays);
        System.out.println("주말: " + weekend);
        System.out.println("모든 요일: " + allDays);
        System.out.println("비어 있음: " + none.isEmpty());

        // 추가
        none.add(Day.TUE);
        none.add(Day.WED);
        System.out.println("추가된 none: " + none);

        // 제거
        none.remove(Day.WED);
        System.out.println("제거 후 none: " + none);

        // 포함 여부
        System.out.println("none에 MON 있음? " + none.contains(Day.MON));
        System.out.println("none에 TUE 있음? " + none.contains(Day.TUE));
    }
}

