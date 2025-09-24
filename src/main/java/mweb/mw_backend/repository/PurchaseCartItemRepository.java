package mweb.mw_backend.repository;

import mweb.mw_backend.entity.PurchaseCartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseCartItemRepository extends JpaRepository<PurchaseCartItem,Long> {
}
