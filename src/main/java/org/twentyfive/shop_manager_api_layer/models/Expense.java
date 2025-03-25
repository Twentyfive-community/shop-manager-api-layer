package org.twentyfive.shop_manager_api_layer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.utilities.classes.enums.PaymentMethod;
import twentyfive.twentyfiveadapter.models.msUserBusinessModels.BusinessUser;

import java.time.LocalDate;
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

    @Column(name = "value") //valore della spesa
    private double value;

    @Column(name = "ref_time")
    private LocalDate refTime;

    @Column(name = "buy_time") //data della spesa
    private LocalDateTime buyTime;

    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "paid")
    private boolean paid;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "business_id", referencedColumnName = "business_id"),
            @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    })
    private BusinessUser worker;

}
