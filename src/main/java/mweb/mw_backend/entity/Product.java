package mweb.mw_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import mweb.mw_backend.enumeration.ProductCondition;
import mweb.mw_backend.enumeration.ProductStatus;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", unique = true, nullable = false)
    private Long code;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProductStatus productStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition", nullable = false, length = 20)
    private ProductCondition productCondition;

    @NotNull(message = "No puede ser nulo")
    @NotBlank(message = "No puede estar en blanco")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "No puede ser nulo")
    @NotBlank(message = "No puede estar en blanco")
    @Column(name = "stock", nullable = false)
    private Long stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private DetailProduct detailProduct;
}
