package com.hab.blog.feature.v1.location.dto;

import lombok.Data;

@Data
public class TencentGeoIpResponse {

    private int status;
    private String message;
    private String requestId;
    private Result result;

    // Getters and Setters

    @Data
    public static class Result {
        private AdInfo adInfo;
        private String ip;
        private Location location;

        // Getters and Setters
    }

    @Data
    public static class AdInfo {
        private int adcode;
        private String city;
        private String district;
        private String nation;
        private int nationCode;
        private String province;
    }

    @Data
    public static class Location {
        private double lat;
        private double lng;
    }
}
