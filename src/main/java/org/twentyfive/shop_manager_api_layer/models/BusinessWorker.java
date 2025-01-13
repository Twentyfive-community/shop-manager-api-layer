package org.twentyfive.shop_manager_api_layer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.models.ids.BusinessWorkerId;

@Entity
@Table(name = "business_workers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessWorker {
    @EmbeddedId
    private BusinessWorkerId id; // ID della relazione

    @Column(name = "role", nullable = false)
    private String role; // Ruolo specifico del Worker nel Business
}
