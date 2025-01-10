package org.twentyfive.shop_manager.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name ="time_slots")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") //ID della fascia, generata automaticamente
    private Long id;

    @Column(name = "name", nullable = false) //nome della fascia
    private String name;

    @Column(name = "description") //Descrizione della fascia
    private String description;

    @Column(name = "start_time")
    private LocalTime start; //Orario inizio fascia

    @Column(name = "end_time")
    private LocalTime end; //Orario fine fascia

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business; //Una fascia è associata a una ed una sola attività

    @OneToMany(mappedBy = "id")
    private List<CashRegister> cashRegisters; //Una fascia è associata a più chiusure cassa


}
