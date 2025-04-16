package com.hab.blog.feature.v1.entities.entitlements.repository;

import com.hab.blog.feature.v1.entities.entitlements.SubscriptionPlan;
import com.hab.blog.feature.v1.entities.entitlements.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {

    List<SubscriptionPlan> findByIsActive(boolean isActive);

    Optional<SubscriptionPlan> findByPlanName(String planName);

    @Query("SELECT sp FROM SubscriptionPlan sp WHERE sp.planType = :planType AND sp.isActive = true")
    List<SubscriptionPlan> findActivePlansByType(@Param("planType") SubscriptionType planType);
}
