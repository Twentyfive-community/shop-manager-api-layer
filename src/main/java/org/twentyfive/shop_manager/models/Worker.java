package org.twentyfive.shop_manager.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name ="workers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") //ID del dipendente, generata automaticamente
    private Long id;

    @Column(name = "keycloak_id") //ID del dipendente, generata automaticamente
    private String keycloakId;

    @Column(name="role") //Ruolo, capo, dipendente semplice ecc, uguale a Keycloak
    private String role;

    @Column(name = "name", nullable = false) //nome della persona
    private String name;

    @Column(name = "last_name", nullable = false) //cognome della persona
    private String lastName;

    @Column(name = "email", nullable = false) //email della persona
    private String email;

    @Column(name = "phone_number") //telefono
    private String phoneNumber;

    @JsonIgnore
    @ManyToMany(mappedBy = "workers")
    private List<Business> workFor;

    @OneToMany(mappedBy = "closedBy")
    private List<CashRegister> closedRegisters;

    @OneToMany(mappedBy = "updatedBy")
    private List<CashRegister> updatedRegisters;

}
