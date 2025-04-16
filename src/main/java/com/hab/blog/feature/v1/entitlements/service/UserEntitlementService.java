package com.hab.blog.feature.v1.entitlements.service;

import com.hab.blog.feature.v1.entitlements.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserEntitlementService {

    List<UserEntitlementResponse> getUserEntitlements(String username);

    List<UserSubscriptionResponse> getUserSubscriptions(String username);

    UserSubscriptionResponse upgradeSubscription(String username, SubscriptionUpgradeRequest request);

    UserEntitlementResponse purchaseEntitlement(String username, EntitlementPurchaseRequest request);

    List<PromotionResponse> getActivePromotions();

    UserPromotionResponse redeemPromotion(String username, PromotionRedeemRequest request);
}

