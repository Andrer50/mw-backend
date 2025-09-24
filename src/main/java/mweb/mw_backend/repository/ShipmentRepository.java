package mweb.mw_backend.repository;

import mweb.mw_backend.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment,Long> {
}
