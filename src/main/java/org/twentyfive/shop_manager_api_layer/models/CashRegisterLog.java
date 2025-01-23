package org.twentyfive.shop_manager_api_layer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.auditable.Auditable;

import java.time.LocalDateTime;

@Entity
@Table(name ="cash_register_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashRegisterLog extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID generato automaticamente
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cash_register_id") // Colonna che fa riferimento a CashRegister
    private CashRegister cashRegister;
}
