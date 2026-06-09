package com.ecommerce.repository;

import com.ecommerce.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCode(String code);
    boolean existsByCode(String code);

    @Query("SELECT c FROM Coupon c WHERE c.code = :code AND c.active = true AND c.startDate <= :date AND c.endDate >= :date")
    Optional<Coupon> findValidCoupon(@Param("code") String code, @Param("date") LocalDate date);
}
