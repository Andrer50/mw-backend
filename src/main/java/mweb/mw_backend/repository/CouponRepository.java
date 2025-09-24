package mweb.mw_backend.repository;

import mweb.mw_backend.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCode(String code);
    
    @Query("SELECT c FROM Coupon c WHERE c.isActive = true AND c.startDate <= :currentDate AND c.endDate >= :currentDate AND c.timesUsed < c.usageLimit")
    List<Coupon> findValidCoupons(@Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT c FROM Coupon c WHERE c.code = :code AND c.isActive = true AND c.startDate <= :currentDate AND c.endDate >= :currentDate AND c.timesUsed < c.usageLimit")
    Optional<Coupon> findValidCouponByCode(@Param("code") String code, @Param("currentDate") LocalDate currentDate);
}