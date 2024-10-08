package com.hab.blog.feature.v1.home;

import com.hab.blog.feature.v1.home.dto.HomePageResponse;
import com.hab.blog.feature.v1.home.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
    @Autowired
    private HomeService homeService;

    @GetMapping
    public ResponseEntity<HomePageResponse> getHomePageData() {
        HomePageResponse response = homeService.getHomePageData();
        return ResponseEntity.ok(response);
    }
}
