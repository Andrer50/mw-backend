package mweb.mw_backend.repository;

import mweb.mw_backend.entity.Order;
import mweb.mw_backend.enumeration.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByOrderStatus(OrderStatus status);
    List<Order> findByCreatedDateBetween(LocalDate startDate, LocalDate endDate);
    List<Order> findByUserIdAndOrderStatus(Long userId, OrderStatus status);
}
