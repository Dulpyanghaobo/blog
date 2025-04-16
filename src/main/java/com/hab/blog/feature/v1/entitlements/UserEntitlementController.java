package com.hab.blog.feature.v1.entitlements;

import com.hab.blog.feature.v1.entitlements.dto.*;
import com.hab.blog.feature.v1.entitlements.service.UserEntitlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/entitlements")
public class UserEntitlementController {

    @Autowired
    private UserEntitlementService userEntitlementService;

    // 获取用户所有的权益
    @GetMapping("/{username}")
    public ResponseEntity<List<UserEntitlementResponse>> getUserEntitlements(@PathVariable String username) {
        List<UserEntitlementResponse> entitlements = userEntitlementService.getUserEntitlements(username);
        return ResponseEntity.ok(entitlements);
    }

    // 获取用户的订阅信息
    @GetMapping("/{username}/subscriptions")
    public ResponseEntity<List<UserSubscriptionResponse>> getUserSubscriptions(@PathVariable String username) {
        List<UserSubscriptionResponse> subscriptions = userEntitlementService.getUserSubscriptions(username);
        return ResponseEntity.ok(subscriptions);
    }

    // 用户升级订阅
    @PostMapping("/{username}/subscriptions/upgrade")
    public ResponseEntity<UserSubscriptionResponse> upgradeSubscription(@PathVariable String username,
                                                                        @RequestBody SubscriptionUpgradeRequest request) {
        UserSubscriptionResponse upgradedSubscription = userEntitlementService.upgradeSubscription(username, request);
        return ResponseEntity.ok(upgradedSubscription);
    }

    // 用户购买一次性权益
    @PostMapping("/{username}/entitlements/purchase")
    public ResponseEntity<UserEntitlementResponse> purchaseEntitlement(@PathVariable String username,
                                                                       @RequestBody EntitlementPurchaseRequest request) {
        UserEntitlementResponse entitlement = userEntitlementService.purchaseEntitlement(username, request);
        return ResponseEntity.ok(entitlement);
    }

    // 获取当前促销活动
    @GetMapping("/promotions")
    public ResponseEntity<List<PromotionResponse>> getActivePromotions() {
        List<PromotionResponse> promotions = userEntitlementService.getActivePromotions();
        return ResponseEntity.ok(promotions);
    }

    // 用户参与促销
    @PostMapping("/{username}/promotions/redeem")
    public ResponseEntity<UserPromotionResponse> redeemPromotion(@PathVariable String username,
                                                                 @RequestBody PromotionRedeemRequest request) {
        UserPromotionResponse promotionResponse = userEntitlementService.redeemPromotion(username, request);
        return ResponseEntity.ok(promotionResponse);
    }
}

