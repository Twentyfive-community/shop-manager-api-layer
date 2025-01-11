package org.twentyfive.shop_manager.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name ="businesses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") //ID dell'attività, generata automaticamente
    private Long id;

    @Column(name = "name", nullable = false) //nome dell'attività
    private String name;

    // Relazione ManyToMany con i dipendenti
    @ManyToMany
    @JoinTable(
            name = "business_workers",  // Nome della tabella di JOIN
            joinColumns = @JoinColumn(name = "business_id"),  // La colonna che si riferisce alla chiave primaria di attività
            inverseJoinColumns = @JoinColumn(name = "worker_id")  // La colonna che si riferisce alla chiave primaria di dipendente
    )
    private List<Worker> workers;

    // Relazione ManyToMany con i fornitori
    @ManyToMany
    @JoinTable(
            name = "business_suppliers",  // Nome della tabella di JOIN
            joinColumns = @JoinColumn(name = "business_id"),  // La colonna che si riferisce alla chiave primaria di attività
            inverseJoinColumns = @JoinColumn(name = "supplier_id")  // La colonna che si riferisce alla chiave primaria di fornitore
    )
    private List<Supplier> suppliers;

    @OneToMany(mappedBy = "business")
    private List<CashRegister> cashRegisters;

    @OneToMany(mappedBy = "business")
    private List<TimeSlot> timeSlots;

    @OneToMany(mappedBy = "business")
    private List<Expense> expenses;
}
