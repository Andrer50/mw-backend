package mweb.mw_backend.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String name;
    String lastName;
    String address;
    String cel;
    String email;
    String password;

    //Falta agregar posiblemente country,city,..
}
