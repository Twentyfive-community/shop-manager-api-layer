package org.twentyfive.shop_manager.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name ="cash_register_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashRegisterLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID generato automaticamente
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cash_register_id") // Colonna che fa riferimento a CashRegister
    private CashRegister cashRegister;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; //Ora di creazione di questo log
}
