package mweb.mw_backend.repository;

import mweb.mw_backend.entity.PurchaseCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseCartRepository extends JpaRepository<PurchaseCart,Long> {
    Optional<PurchaseCart> findByUserIdAndIsActiveTrue(Long userId);
    Optional<PurchaseCart> findByUserId(Long userId);
}
