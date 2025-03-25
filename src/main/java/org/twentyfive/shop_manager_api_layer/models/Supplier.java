package org.twentyfive.shop_manager_api_layer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import twentyfive.twentyfiveadapter.models.msUserBusinessModels.Business;

import java.util.Set;

@Entity
@Table(name ="suppliers",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "business_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"business", "group", "expenses"})
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "disabled")
    private boolean disabled = false;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @ManyToOne
    @JoinColumn(name ="group_id")
    private SupplierGroup group;

    @OneToMany(mappedBy = "supplier")
    @JsonIgnore
    @ToString.Exclude
    private Set<Expense> expenses;
}

