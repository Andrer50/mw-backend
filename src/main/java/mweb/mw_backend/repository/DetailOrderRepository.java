package mweb.mw_backend.repository;

import mweb.mw_backend.entity.DetailOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailOrderRepository extends JpaRepository<DetailOrder,Long> {
}
