package org.twentyfive.shop_manager_api_layer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name ="expenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") //ID della spesa, generata automaticamente
    private Long id;

    @Column(name = "description") //descrizione della spesa
    private String description;

    @Column(name = "value") //valore della spesa
    private double value;

    @Column(name = "buy_time") //data della spesa
    private LocalDateTime buyTime;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

}
