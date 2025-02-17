package org.twentyfive.shop_manager_api_layer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.utilities.classes.enums.PaymentMethod;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name ="customer_expenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") //ID della spesa, generata automaticamente
    private Long id;

    @Column(name = "value") //valore della spesa
    private double value;

    @Column(name = "ref_time")
    private LocalDate refTime;

    @Column(name = "buy_time") //data della spesa
    private LocalDateTime buyTime;

    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "issued")
    private boolean issued;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "business_id", referencedColumnName = "business_id"),
            @JoinColumn(name = "worker_id", referencedColumnName = "worker_id")
    })
    private BusinessWorker worker;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
