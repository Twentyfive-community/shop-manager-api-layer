package org.twentyfive.shop_manager_api_layer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name ="businesses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"workersWithRoles", "suppliers", "supplierGroups", "cashRegisters", "timeSlots"})
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "id.business", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BusinessWorker> workersWithRoles;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Supplier> suppliers;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SupplierGroup> supplierGroups;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "business")
    private Set<CashRegister> cashRegisters;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TimeSlot> timeSlots;
}