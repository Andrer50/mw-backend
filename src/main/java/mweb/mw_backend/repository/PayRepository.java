package mweb.mw_backend.repository;

import mweb.mw_backend.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository extends JpaRepository<Pay,Long> {
}
