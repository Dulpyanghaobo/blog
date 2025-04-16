package com.hab.blog.feature.v1.entities.entitlements.repository;

import com.hab.blog.feature.v1.entities.entitlements.UserEntitlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserEntitlementRepository extends JpaRepository<UserEntitlement, Long> {

    List<UserEntitlement> findByUser_UserName(String userName);

    @Query("SELECT ue FROM UserEntitlement ue WHERE ue.user.userName = :userName AND ue.entitlementType = :entitlementType")
    Optional<UserEntitlement> findByUserNameAndEntitlementType(@Param("userName") String userName, @Param("entitlementType") String entitlementType);

    @Query("SELECT ue FROM UserEntitlement ue WHERE ue.user.userName = :userName AND ue.remainingCount > 0")
    List<UserEntitlement> findActiveEntitlementsByUserName(@Param("userName") String userName);
}
