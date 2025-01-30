package org.twentyfive.shop_manager_api_layer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name ="suppliers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") //ID del fornitore, generata automaticamente
    private Long id;

    @Column(name = "name", nullable = false) //nome del fornitore
    private String name;

    @Column(name = "address") //via del fornitore
    private String address;

    @ManyToMany(mappedBy = "suppliers")
    private Set<Business> workFor;

    @OneToMany(mappedBy ="supplier")
    private Set<Expense> expenses;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "id.supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BusinessSupplier> businessSuppliers;

}
