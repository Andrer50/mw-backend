package mweb.mw_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import mweb.mw_backend.enumeration.UserRole;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "No puede ser nulo")
    @NotBlank(message = "No puede estar en blanco")
    @Size(max = 30, message = "El Nombre no puede exceder los 30 caracteres")
    @Column(name = "name")
    private String name;

    @NotNull(message = "No puede ser nulo")
    @Size(max = 50, message = "El Apellido no puede exceder los 50 caracteres")
    @Column(name="last_name", nullable = false)
    private String lastName;

    @NotNull(message = "No puede ser nulo")
    @NotBlank(message = "No puede estar en blanco")
    @Column(name = "email", unique = true)
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="register_date")
    private LocalDateTime registerDate;

    @NotNull(message = "No puede ser nulo")
    @NotBlank(message = "No puede estar en blanco")
    @Column(name="role")
    private UserRole role;

    @NotNull(message="No puede ser nulo")
    @NotBlank(message = "No puede estar en blanco")
    @Size(max = 100, message = "La dirección no puede exceder los 100 caracteres")
    @Column(name = "address")
    private String address;

    @NotNull(message = "No puede ser nulo")
    @NotBlank(message = "No puede estar en blanco")
    @Size(max = 11, message = "El Número no puede exceder los 9 caracteres")
    @Column(name="cel")
    private String cel;
}
