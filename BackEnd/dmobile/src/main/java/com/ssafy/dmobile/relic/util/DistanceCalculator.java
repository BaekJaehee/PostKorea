package com.ssafy.dmobile.relic.util;

import java.math.BigDecimal;

public class DistanceCalculator {
    // 지구의 반지름 (단위: 킬로미터)
    private static final double EARTH_RADIUS = 6371.0;

    // 위도와 경도를 이용하여 두 지점 간의 거리를 계산
    public static double calculateDistance(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2) {
        double lat1Rad = Math.toRadians(lat1.doubleValue());
        double lon1Rad = Math.toRadians(lon1.doubleValue());
        double lat2Rad = Math.toRadians(lat2.doubleValue());
        double lon2Rad = Math.toRadians(lon2.doubleValue());

        // 위도와 경도의 차이
        double latDiff = lat2Rad - lat1Rad;
        double lonDiff = lon2Rad - lon1Rad;

        // 위도와 경도의 차이에 대한 삼각함수 계산
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);

        // 중심각 계산
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 두 지점 간의 거리 계산 (단위: 킬로미터)
        double distance = EARTH_RADIUS * c;

        return distance;
    }

    public static void main(String[] args) {
        BigDecimal lat1 = new BigDecimal("37.5665");
        BigDecimal lon1 = new BigDecimal("126.9780");
        BigDecimal lat2 = new BigDecimal("35.6895");
        BigDecimal lon2 = new BigDecimal("139.6917");

        double distance = calculateDistance(lat1, lon1, lat2, lon2);
        System.out.println("Distance between the two points: " + distance + " km");
    }
}
