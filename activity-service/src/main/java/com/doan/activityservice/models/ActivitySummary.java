package com.doan.activityservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivitySummary {

    private String employeeId;
    private double totalDistance; // Tổng khoảng cách (đơn vị: mét)
    private long totalMovingTime; // Tổng thời gian di chuyển (đơn vị: giây)
    private int totalActivities; // Tổng số hoạt động
    private double averageSpeed; // Tốc độ trung bình (đơn vị: m/s)

    public ActivitySummary(String employeeId, double totalDistance, long totalMovingTime, int totalActivities) {
        this.employeeId = employeeId;
        this.totalDistance = totalDistance;
        this.totalMovingTime = totalMovingTime;
        this.totalActivities = totalActivities;
        this.averageSpeed = totalMovingTime > 0 ? totalDistance / totalMovingTime : 0;
    }

    // Phương thức để chuyển đổi tổng khoảng cách từ mét sang kilômét
    public double getTotalDistanceInKm() {
        return totalDistance / 1000;
    }

    // Phương thức để chuyển đổi tổng thời gian di chuyển từ giây sang giờ
    public double getTotalMovingTimeInHours() {
        return totalMovingTime / 3600.0;
    }

    // Phương thức để tính tốc độ trung bình theo km/h
    public double getAverageSpeedInKmh() {
        return averageSpeed * 3.6; // Chuyển đổi từ m/s sang km/h
    }
}
