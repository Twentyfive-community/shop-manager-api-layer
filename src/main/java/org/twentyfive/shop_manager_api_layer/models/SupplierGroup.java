package org.twentyfive.shop_manager_api_layer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="supplier_groups")
public class SupplierGroup {

    @Id
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "group")
    private Set<Supplier> suppliers;


}
