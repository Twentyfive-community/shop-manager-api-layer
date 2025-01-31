package org.twentyfive.shop_manager_api_layer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name ="suppliers",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "business_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // ID del fornitore, generata automaticamente
    private Long id;

    @Column(name = "name", nullable = false) // nome del fornitore
    private String name;

    @Column(name = "address") // indirizzo del fornitore
    private String address;

    @Column(name = "disabled")
    private boolean disabled = false;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;


    @OneToMany(mappedBy = "supplier")
    private Set<Expense> expenses;
}
