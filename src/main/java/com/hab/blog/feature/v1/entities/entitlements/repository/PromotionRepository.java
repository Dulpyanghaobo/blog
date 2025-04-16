package com.hab.blog.feature.v1.entities.entitlements.repository;

import com.hab.blog.feature.v1.entities.entitlements.Promotion;
import com.hab.blog.feature.v1.entities.entitlements.PromotionType;
import com.hab.blog.feature.v1.entities.entitlements.TargetUserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    List<Promotion> findByPromotionType(PromotionType promotionType);

    @Query("SELECT p FROM Promotion p WHERE p.targetUserGroup = :targetUserGroup")
    List<Promotion> findActivePromotionsByTargetGroup(@Param("targetUserGroup") TargetUserGroup targetUserGroup);

    @Query("SELECT p FROM Promotion p WHERE p.startDate <= :currentDate AND p.endDate >= :currentDate")
    List<Promotion> findActivePromotions(@Param("currentDate") LocalDate currentDate);
}
