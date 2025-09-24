package mweb.mw_backend.repository;

import mweb.mw_backend.entity.DetailProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailProductRepository extends JpaRepository<DetailProduct,Long> {
}
