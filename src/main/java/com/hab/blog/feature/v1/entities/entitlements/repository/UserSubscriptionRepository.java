package com.hab.blog.feature.v1.entities.entitlements.repository;

import com.hab.blog.feature.v1.entities.entitlements.SubscriptionType;
import com.hab.blog.feature.v1.entities.entitlements.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    Optional<UserSubscription> findByUser_UserName(String userName);

    @Query("SELECT us FROM UserSubscription us WHERE us.user.userName = :userName AND us.subscriptionStatus = 'ACTIVE'")
    List<UserSubscription> findActiveSubscriptionsByUserName(@Param("userName") String userName);

    @Query("SELECT us FROM UserSubscription us WHERE us.user.userName = :userName AND us.subscriptionType = :subscriptionType")
    Optional<UserSubscription> findByUserNameAndSubscriptionType(@Param("userName") String userName, @Param("subscriptionType") SubscriptionType subscriptionType);
}

