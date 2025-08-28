package mweb.mw_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pays")
public class Pay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentMethod;
    /*Monto sin impuestos*/
    private Double subtotal;
    private Double igv;
    /*Monto + IGV*/
    private Double total;
    private LocalDate shippingDate;
    private Order order;
}
