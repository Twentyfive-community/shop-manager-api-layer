package org.twentyfive.shop_manager_api_layer.models.ids;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.models.CashRegister;
import org.twentyfive.shop_manager_api_layer.models.ComposedEntry;

import java.io.Serializable;

/**
 * Chiave composta per la tabella Voce composta con valore.
 * Sono quelle chiavi che vanno nella chiusura cassa che dipendono da altre voci.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ComposedEntryClosureId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "composed_entry_id")
    private ComposedEntry composedEntry;  // Associazione a Entry

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "register_id")
    private CashRegister cashRegister;  // Associazione a CashRegister
}
