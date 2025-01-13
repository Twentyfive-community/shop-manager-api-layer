package org.twentyfive.shop_manager_api_layer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name ="workers",
uniqueConstraints = @UniqueConstraint(columnNames ={"keycloak_id"})
)
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

    @Column(name = "first_name", nullable = false) //nome della persona
    private String firstName;

    @Column(name = "last_name", nullable = false) //cognome della persona
    private String lastName;

    @Column(name = "email", nullable = false) //email della persona
    private String email;

    @Column(name = "phone_number") //telefono
    private String phoneNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "id.worker", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BusinessWorker> businessRoles; // Ruoli del worker nei business

    @JsonIgnore
    @OneToMany(mappedBy = "closedBy")
    private List<CashRegister> closedRegisters;

    @JsonIgnore
    @OneToMany(mappedBy = "updatedBy")
    private List<CashRegister> updatedRegisters;

}
