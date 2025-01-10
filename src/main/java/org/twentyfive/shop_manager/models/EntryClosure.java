package org.twentyfive.shop_manager.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="entry_closures")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntryClosure {

    @EmbeddedId
    private EntryClosureId id;  // Chiave primaria composta

    @Column(name = "value")
    private double value; //Valore della voce che andiamo a mettere nella chiusura cassa
}
