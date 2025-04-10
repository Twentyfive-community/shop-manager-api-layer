package org.twentyfive.shop_manager_api_layer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.twentyfive.shop_manager_api_layer.utilities.classes.enums.Operation;
import twentyfive.twentyfiveadapter.models.msUserBusinessModels.Business;

import java.util.List;
import java.util.Set;

@Entity
@Table(name ="entries",
        uniqueConstraints = @UniqueConstraint(columnNames = {"label", "business_id"})
)
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

    @Column(name ="required")
    private boolean required;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "id.entry")  // mappedBy si riferisce al campo 'entry' in EntryClosureId, chiave composta
    private List<EntryClosure> entryClosures;

    @ManyToOne
    private Business business;

}
