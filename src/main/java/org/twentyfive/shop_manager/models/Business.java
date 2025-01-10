package org.twentyfive.shop_manager.models;

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
            name = "business_employers",  // Nome della tabella di JOIN
            joinColumns = @JoinColumn(name = "business_id"),  // La colonna che si riferisce alla chiave primaria di attività
            inverseJoinColumns = @JoinColumn(name = "employer_id")  // La colonna che si riferisce alla chiave primaria di dipendente
    )
    private List<Employer> employers;

    // Relazione ManyToMany con i fornitori
    @ManyToMany
    @JoinTable(
            name = "business_suppliers",  // Nome della tabella di JOIN
            joinColumns = @JoinColumn(name = "business_id"),  // La colonna che si riferisce alla chiave primaria di attività
            inverseJoinColumns = @JoinColumn(name = "supplier_id")  // La colonna che si riferisce alla chiave primaria di fornitore
    )
    private List<Supplier> suppliers;

    @OneToMany(mappedBy = "id")
    private List<CashRegister> cashRegisters;

    @OneToMany(mappedBy = "id")
    private List<TimeSlot> timeSlots;

    @OneToMany(mappedBy = "id")
    private List<Expense> expenses;
}
