package org.twentyfive.shop_manager_api_layer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.auditable.Auditable;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name ="cash_registers",
        indexes = {
                @Index(name = "idx_cash_register_ref_time", columnList = "ref_time")
        },
        uniqueConstraints = @UniqueConstraint(columnNames ={"ref_time","time_slot_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashRegister extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") //ID dell'attività, generata automaticamente
    private Long id;

    @Column(name = "ref_time") //tempo di riferimento di quella chiusura cassa
    private LocalDate refTime;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business; //Una chiusura cassa è associata a una ed una sola attività

    @OneToMany(mappedBy = "id.cashRegister")
    private List<EntryClosure> entryClosures; //lista delle voci presente nella ChiusuraCassa

    @OneToMany(mappedBy ="id.cashRegister")
    private List<ComposedEntryClosure> composedEntryClosures; //lista delle voci composte presenti nella chiusura cassa

    @ManyToOne
    @JoinColumn(name= "time_slot_id")
    private TimeSlot timeSlot; //fascia scelta per quella chiusura cassa

    @ManyToOne
    @JoinColumn(name = "closed_by_id") // Dipendente che ha fatto la chiusura cassa
    private Worker closedBy;

    @ManyToOne
    @JoinColumn(name = "updated_by_id") // Dipendente che ha fatto l'ultima modifica della chiusura cassa, può essere null
    private Worker updatedBy;

    @OneToMany(mappedBy="cashRegister")
    private List<CashRegisterLog> logs; //log delle chiusura casse


}
