package org.twentyfive.shop_manager_api_layer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.twentyfive.shop_manager_api_layer.auditable.Auditable;
import org.twentyfive.shop_manager_api_layer.utilities.classes.Report;

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
    @Column(name = "id") // ID dell'attività, generata automaticamente
    private Long id;

    @Column(name = "ref_time") // Tempo di riferimento di quella chiusura cassa
    private LocalDate refTime;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business; // Una chiusura cassa è associata a una ed una sola attività

    @OneToMany(mappedBy = "id.cashRegister", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntryClosure> entryClosures; // Lista delle voci presente nella ChiusuraCassa

    @OneToMany(mappedBy ="id.cashRegister", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComposedEntryClosure> composedEntryClosures; // Lista delle voci composte presenti nella chiusura cassa

    @ManyToOne
    @JoinColumn(name= "time_slot_id")
    private TimeSlot timeSlot; // Fascia scelta per quella chiusura cassa

    @ManyToOne
    @JoinColumn(name = "closed_by_id") // Dipendente che ha fatto la chiusura cassa
    private Worker closedBy;

    @ManyToOne
    @JoinColumn(name = "updated_by_id") // Dipendente che ha fatto l'ultima modifica della chiusura cassa, può essere null
    private Worker updatedBy;

    @ToString.Exclude
    @OneToMany(mappedBy="cashRegister", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CashRegisterLog> logs; // Log delle chiusura casse

    public Report getReport(){
        Report report = new Report();
        for (EntryClosure entryClosure : entryClosures) {
            if (entryClosure.getId().getEntry().getOperation().getSymbol().equals("+")){
                report.setTotalRevenue(report.getTotalRevenue()+entryClosure.getValue());
            } else if (entryClosure.getId().getEntry().getOperation().getSymbol().equals("-")){
                report.setTotalCost(report.getTotalCost()+entryClosure.getValue());
            }
        }
        for (ComposedEntryClosure composedEntryClosure : composedEntryClosures) {
            if (composedEntryClosure.getId().getComposedEntry().getOperation().getSymbol().equals("+")){
                report.setTotalRevenue(report.getTotalRevenue()+ composedEntryClosure.getTotalValue());
            } else if (composedEntryClosure.getId().getComposedEntry().getOperation().getSymbol().equals("-")){
                report.setTotalCost(report.getTotalCost() + composedEntryClosure.getTotalValue());
            }
        }
        return report;
    }
}
