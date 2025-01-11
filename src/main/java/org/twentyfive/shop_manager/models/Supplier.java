package org.twentyfive.shop_manager.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<Business> workFor;

    @OneToMany(mappedBy ="supplier")
    private List<Expense> expenses;

}
