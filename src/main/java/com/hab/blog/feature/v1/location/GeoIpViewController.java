package com.hab.blog.feature.v1.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/location")
public class GeoIpViewController {

    @Autowired
    private GeoIpService geoIpService;

    // 接收IP并返回位置信息
    @GetMapping("/ip")
    public ResponseEntity<String> getLocationByIp(@RequestParam String ip) {
        try {
            // 调用 GeoIpService 获取地理位置
            String location = geoIpService.getGeoLocation(ip);
            return ResponseEntity.ok(location);
        } catch (Exception e) {
            // 如果发生错误，返回错误信息
            return ResponseEntity.status(500).body("Error retrieving location: " + e.getMessage());
        }
    }
}
