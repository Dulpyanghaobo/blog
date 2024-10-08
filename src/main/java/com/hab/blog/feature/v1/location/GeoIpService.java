package com.hab.blog.feature.v1.location;

import com.hab.blog.feature.v1.location.dto.TencentGeoIpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeoIpService {

    private final String apiKey = "GEFBZ-TZZ67-OFMXQ-PKTR6-NF6OQ-LVFBD";
    private final String apiUrl = "https://apis.map.qq.com/ws/location/v1/ip";

    public String getGeoLocation(String ip) {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "?ip=" + ip + "&key=" + apiKey;

        TencentGeoIpResponse response = restTemplate.getForObject(url, TencentGeoIpResponse.class);
        if (response != null && response.getResult() != null) {
            return response.getResult().getAdInfo().getProvince() + ", " + response.getResult().getAdInfo().getCity();
        }

        return "Unknown Location";
    }
}

