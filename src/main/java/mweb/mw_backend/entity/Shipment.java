package mweb.mw_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import mweb.mw_backend.enumeration.ShippingStatus;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shipments")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mailingAddress;
    private String city;
    private String zipCode;
    private ShippingStatus shippingStatus;
    private LocalDate deliveryDate;
    private Order order;
}
