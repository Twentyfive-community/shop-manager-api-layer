package org.twentyfive.shop_manager_api_layer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

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
    @JsonIgnore
    @JoinTable(
            name = "business_workers",  // Nome della tabella di JOIN
            joinColumns = @JoinColumn(name = "business_id"),  // La colonna che si riferisce alla chiave primaria di attività
            inverseJoinColumns = @JoinColumn(name = "worker_id"), // La colonna che si riferisce alla chiave primaria di dipendente
            indexes = {  // Indici sulla tabella di JOIN
                    @Index(name = "idx_business_worker", columnList = "business_id")  // Indice sulla colonna 'worker_id'
            },
            uniqueConstraints = @UniqueConstraint(columnNames = {"business_id", "worker_id"})  // Unicità della combinazione business_id + worker_id
    )
    private Set<Worker> workers;

    // Relazione ManyToMany con i fornitori
    @ManyToMany
    @JoinTable(
            name = "business_suppliers",  // Nome della tabella di JOIN
            joinColumns = @JoinColumn(name = "business_id"),  // La colonna che si riferisce alla chiave primaria di attività
            inverseJoinColumns = @JoinColumn(name = "supplier_id") //La colonna che si riferisce alla chiave primaria di fornitore
    )
    private Set<Supplier> suppliers;

    @OneToMany(mappedBy = "business")
    private Set<CashRegister> cashRegisters;

    @JsonIgnore
    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TimeSlot> timeSlots;

    @OneToMany(mappedBy = "business")
    private Set<Expense> expenses;
}
