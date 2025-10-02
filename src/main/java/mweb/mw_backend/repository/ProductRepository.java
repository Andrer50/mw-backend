package mweb.mw_backend.repository;

import mweb.mw_backend.entity.Product;
import mweb.mw_backend.enumeration.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
    Page<Product> findByCategoryIdAndIdNot(Long categoryId, Long excludeId, Pageable pageable);
    Page<Product> findByProductStatus(ProductStatus status, Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:search% OR p.detailProduct.description LIKE %:search%")
    Page<Product> findBySearchTerm(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND (p.name LIKE %:search% OR p.detailProduct.description LIKE %:search%)")
    Page<Product> findByCategoryIdAndSearchTerm(@Param("categoryId") Long categoryId, @Param("search") String search, Pageable pageable);
    
    List<Product> findTop8ByOrderByIdDesc(); // Productos más recientes
    
    // Métodos para contar
    long countByCategoryId(Long categoryId);
    
    // Verificar existencia por código
    boolean existsByCode(Long code);
}
