package com.hab.blog.feature.v1.entities.entitlements.repository;

import com.hab.blog.feature.v1.entities.entitlements.UserPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserPromotionRepository extends JpaRepository<UserPromotion, Long> {

    List<UserPromotion> findByUser_UserName(String userName);

    @Query("SELECT up FROM UserPromotion up WHERE up.user.userName = :userName AND up.isRedeemed = false")
    List<UserPromotion> findUnredeemedPromotionsByUserName(@Param("userName") String userName);

    @Query("SELECT up FROM UserPromotion up WHERE up.promotion.id = :promotionId AND up.user.userName = :userName")
    Optional<UserPromotion> findByUserNameAndPromotionId(@Param("userName") String userName, @Param("promotionId") Long promotionId);
}
