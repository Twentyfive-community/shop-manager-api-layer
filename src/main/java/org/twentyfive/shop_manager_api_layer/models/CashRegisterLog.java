package org.twentyfive.shop_manager_api_layer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.auditable.Auditable;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name ="cash_register_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashRegisterLog extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cash_register_id") // Riferimento al registro originale
    private CashRegister cashRegister;

    @Column(name = "ref_time")
    private LocalDate refTime;

    @Column(name = "time_slot_name")
    private String timeSlotName;

    @Column(name = "closed_by")
    private String closedBy;

    @Column(name = "updated_by")
    private String updatedBy;

}

