package org.twentyfive.shop_manager_api_layer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name ="supplier_groups",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "business_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"business", "suppliers"})
public class SupplierGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "group")
    @JsonIgnore
    @ToString.Exclude
    private List<Supplier> suppliers;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;
}

