package org.twentyfive.shop_manager_api_layer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name ="composed_entries",
        uniqueConstraints = @UniqueConstraint(columnNames = {"label"})  // Unicità label
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComposedEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") //ID della voce, generata automaticamente
    private Long id;

    @Column(name = "label", nullable = false)
    private String label; //Nome della voce

    @JsonIgnore
    @OneToMany(mappedBy = "id.composedEntry")  // mappedBy si riferisce al campo 'entry' in EntryClosureId, chiave composta
    private List<ComposedEntryClosure> composedEntryClosures;

    // Relazione ManyToMany con le voci
    @ManyToMany
    @JoinTable(
            name = "composed_entry_linked",  // Nome della tabella di JOIN
            joinColumns = @JoinColumn(name = "composed_entry_id"),  // La colonna che si riferisce alla chiave primaria di attività
            inverseJoinColumns = @JoinColumn(name = "entry_id")  // La colonna che si riferisce alla chiave primaria di fornitore
    )
    private Set<Entry> entries;
}
