package org.twentyfive.shop_manager_api_layer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Entity
@Table(name ="businesses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") //ID dell'attività, generata automaticamente
    private Long id;

    @Column(name = "name", nullable = false) //nome dell'attività
    private String name;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "id.business", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BusinessWorker> workersWithRoles; // Workers associati con ruoli

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Supplier> suppliers;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "business")
    private Set<CashRegister> cashRegisters;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TimeSlot> timeSlots;


}
