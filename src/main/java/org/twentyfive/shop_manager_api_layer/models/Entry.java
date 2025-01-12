package org.twentyfive.shop_manager_api_layer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.utilities.classes.Operation;

import java.util.List;

@Entity
@Table(name ="entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") //ID della voce, generata automaticamente
    private Long id;

    @Column(name = "label", nullable = false)
    private String label; //Nome della voce

    @Column(name ="operation", nullable = false)
    private Operation operation;

    @JsonIgnore
    @OneToMany(mappedBy = "id.entry")  // mappedBy si riferisce al campo 'entry' in EntryClosureId, chiave composta
    private List<EntryClosure> entryClosures;

    @JsonIgnore
    @ManyToMany(mappedBy="entries") //si riferisce a quali voci composte fa riferimento
    private List<ComposedEntry> composedEntries;

}
