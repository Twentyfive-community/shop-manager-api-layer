package org.twentyfive.shop_manager.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Chiave composta per la tabella Voce con valore.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EntryClosureId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "entry_id")
    private Entry entry;  // Associazione a Entry

    @ManyToOne
    @JoinColumn(name = "register_id")
    private CashRegister cashRegister;  // Associazione a CashRegister
}

