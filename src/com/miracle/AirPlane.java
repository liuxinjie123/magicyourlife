package com.miracle;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 在桥蓝国，有 (N) 座城市。
 * 由于桥蓝国总统 Drump 不支持发展陆路运输，城市和城市之间都是用双向航班来连接的。
 * Tonald Drump，作为新一任的桥蓝国总统，对修改航班时刻表非常感兴趣。他每天都会做这样的事：
 *
 * 选择一个城市；
 * 把该城市已经开通的航班全部取消，引入所有没有开通的航班。
 * 比如说，5 号城市现在有直达 3 号城市和 4 号城市的航班，但没有直达 1 号和 2 号城市的，
 * 那么，在 Drump 进行了修改之后，从 5 号城市可以直达 1 号和 2 号，但不能直达 3 号和 4 号。
 *
 * 但这项改革并不是完全不好的。比如约纽时报就指出，可能会有那么一天，任意两个城市之间都有直达航班相连。
 *
 * 这真的有可能吗？写个程序帮忙算一算。
 */
public class AirPlane {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Country> countryList = new ArrayList<>();
        for (int i=0; i<n; i++) {
            Country country = new Country();
            country.cityCount = scanner.nextInt();
            country.airLineCount = scanner.nextInt();
            if (0 != country.airLineCount) {
                List<AirLine> airLineList = new ArrayList<>();
                for (int j=0; j<country.airLineCount; j++) {
                    int from = scanner.nextInt();
                    int to = scanner.nextInt();
                    airLineList.add(new AirLine(from, to));
                }
                country.airLineList = airLineList;
            }
            countryList.add(country);
        }

        // check
        checkAirLine(countryList);

        return;
    }

    private static void checkAirLine(List<Country> countryList) {
        loop:
        for (int i=1; i<=countryList.size(); i++) {
            Country country = countryList.get(i-1);
            if (country.airLineCount == 0) {
                System.out.println("Case " + i + ": YES");
                continue loop;
            } else {
                for (AirLine airLine : country.airLineList) {
                    if (checkIfExists(airLine, country.airLineList)) {
                        System.out.println("Case " + i + ": YES");
                        continue loop;
                    }
                }
                System.out.println("Case " + i + ": NO");
            }
        }
    }

    static boolean checkIfExists(AirLine air, List<AirLine> airLineList) {
        for (AirLine airLine : airLineList) {
            if (airLine.from == air.from) {
                continue;
            } else {
                if (airLine.from == air.to) {
                    return false;
                }
                if (airLine.to == air.to) {
                    return false;
                }
                if (airLine.to == air.from) {
                    return false;
                }
                air = airLine;
            }
        }
        return true;
    }

    static class Country {
        int cityCount;
        int airLineCount;
        List<AirLine> airLineList;
    }

    static class AirLine {
        int from;
        int to;
        public AirLine() {}
        public AirLine(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }
}
