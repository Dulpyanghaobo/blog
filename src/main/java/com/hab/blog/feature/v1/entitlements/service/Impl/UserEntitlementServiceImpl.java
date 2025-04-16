package com.hab.blog.feature.v1.entitlements.service.Impl;

import com.hab.blog.feature.v1.entities.User.Repository.UserRepository;
import com.hab.blog.feature.v1.entities.entitlements.*;
import com.hab.blog.feature.v1.entities.entitlements.repository.PromotionRepository;
import com.hab.blog.feature.v1.entities.entitlements.repository.UserEntitlementRepository;
import com.hab.blog.feature.v1.entities.entitlements.repository.UserPromotionRepository;
import com.hab.blog.feature.v1.entities.entitlements.repository.UserSubscriptionRepository;
import com.hab.blog.feature.v1.entitlements.dto.*;
import com.hab.blog.feature.v1.entitlements.service.UserEntitlementService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserEntitlementServiceImpl implements UserEntitlementService {

    @Autowired
    private UserSubscriptionRepository userSubscriptionRepository;

    @Autowired
    private UserEntitlementRepository userEntitlementRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private UserPromotionRepository userPromotionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserEntitlementResponse> getUserEntitlements(String username) {
        // 从数据库查询用户的权益信息
        return userEntitlementRepository.findByUser_UserName(username)
                .stream()
                .map(entitlement -> mapToEntitlementResponse(entitlement))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserSubscriptionResponse> getUserSubscriptions(String username) {
        // 查询用户的订阅信息
        return userSubscriptionRepository.findByUser_UserName(username)
                .stream()
                .map(subscription -> mapToSubscriptionResponse(subscription))
                .collect(Collectors.toList());
    }

    @Override
    public UserSubscriptionResponse upgradeSubscription(String username, SubscriptionUpgradeRequest request) {
        // 升级用户的订阅
        UserSubscription subscription = userSubscriptionRepository
                .findByUserNameAndSubscriptionType(username, SubscriptionType.valueOf(request.getSubscriptionType()))
                .orElseThrow(() -> new EntityNotFoundException("Subscription not found"));

        subscription.setSubscriptionType(SubscriptionType.valueOf(request.getSubscriptionType()));
        subscription.setAutoRenew(request.isAutoRenew());
        userSubscriptionRepository.save(subscription);

        return mapToSubscriptionResponse(subscription);
    }

    @Override
    public UserEntitlementResponse purchaseEntitlement(String username, EntitlementPurchaseRequest request) {
        // 用户购买一次性权益
        UserEntitlement entitlement = new UserEntitlement();
        entitlement.setUser(userRepository.findUsersByUserName(username).orElseThrow(() -> new EntityNotFoundException("User not found")));
        entitlement.setEntitlementType(request.getEntitlementType());
        entitlement.setMaxLimit(request.getQuantity());
        entitlement.setUsedCount(0);
        entitlement.setRemainingCount(request.getQuantity());
        userEntitlementRepository.save(entitlement);

        return mapToEntitlementResponse(entitlement);
    }

    @Override
    public List<PromotionResponse> getActivePromotions() {
        // 获取当前活跃的促销活动
        return promotionRepository.findActivePromotions(LocalDate.now())
                .stream()
                .map(promotion -> mapToPromotionResponse(promotion))
                .collect(Collectors.toList());
    }

    @Override
    public UserPromotionResponse redeemPromotion(String username, PromotionRedeemRequest request) {
        // 用户参与促销
        Promotion promotion = promotionRepository.findById(request.getPromotionId())
                .orElseThrow(() -> new EntityNotFoundException("Promotion not found"));

        UserPromotion userPromotion = new UserPromotion();
        userPromotion.setUser(userRepository.findUsersByUserName(username).orElseThrow(() -> new EntityNotFoundException("User not found")));
        userPromotion.setPromotion(promotion);
        userPromotion.setRedeemedAt(Instant.now());
        userPromotion.setRedeemed(false);
        userPromotionRepository.save(userPromotion);

        return mapToUserPromotionResponse(userPromotion);
    }

    // Mapper methods to convert entities to DTOs
    private UserEntitlementResponse mapToEntitlementResponse(UserEntitlement entitlement) {
        UserEntitlementResponse response = new UserEntitlementResponse();
        response.setEntitlementType(entitlement.getEntitlementType());
        response.setMaxLimit(entitlement.getMaxLimit());
        response.setUsedCount(entitlement.getUsedCount());
        response.setRemainingCount(entitlement.getRemainingCount());
        response.setStartDate(entitlement.getStartDate());
        response.setEndDate(entitlement.getEndDate());
        return response;
    }

    private UserSubscriptionResponse mapToSubscriptionResponse(UserSubscription subscription) {
        UserSubscriptionResponse response = new UserSubscriptionResponse();
        response.setSubscriptionType(subscription.getSubscriptionType().toString());
        response.setSubscriptionStatus(subscription.getSubscriptionStatus().toString());
        response.setStartDate(subscription.getStartDate());
        response.setEndDate(subscription.getEndDate());
        response.setAutoRenew(subscription.isAutoRenew());
        return response;
    }

    private PromotionResponse mapToPromotionResponse(Promotion promotion) {
        PromotionResponse response = new PromotionResponse();
        response.setPromotionName(promotion.getPromotionName());
        response.setPromotionType(promotion.getPromotionType());
        response.setDiscountAmount(promotion.getDiscountAmount());
        response.setStartDate(promotion.getStartDate());
        response.setEndDate(promotion.getEndDate());
        response.setTargetUserGroup(promotion.getTargetUserGroup());
        return response;
    }

    private UserPromotionResponse mapToUserPromotionResponse(UserPromotion userPromotion) {
        UserPromotionResponse response = new UserPromotionResponse();
        response.setPromotionName(userPromotion.getPromotion().getPromotionName());
        response.setRedeemed(userPromotion.isRedeemed());
        response.setRedeemedAt(userPromotion.getRedeemedAt());
        return response;
    }
}

