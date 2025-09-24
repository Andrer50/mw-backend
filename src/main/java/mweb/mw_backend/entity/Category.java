package mweb.mw_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(max = 255, message = "El nombre no puede exceder los 255 caracteres")
    @Column(name = "name")
    private String name;

    @NotNull(message = "La descripción no puede ser nulo")
    @NotBlank(message = "La descripción no puede estar en blanco")
    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    @Column(name = "description")
    private String description;

    // Relación con Product
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> productList;
}
