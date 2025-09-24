package mweb.mw_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchase_carts")
public class PurchaseCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cada usuario tiene un carrito activo
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseCartItem> purchaseCartItems = new ArrayList<>();

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    // Método para calcular el total del carrito
    public Double calculateTotal() {
        return purchaseCartItems.stream()
                .mapToDouble(item -> item.getProduct().getDetailProduct().getPrice() * item.getQuantity())
                .sum();
    }

    // Método para obtener el número total de items
    public Integer getTotalItems() {
        return purchaseCartItems.stream()
                .mapToInt(PurchaseCartItem::getQuantity)
                .sum();
    }
}
