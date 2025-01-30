package org.twentyfive.shop_manager_api_layer.models;

import jakarta.persistence.*;
import lombok.*;
import org.twentyfive.shop_manager_api_layer.models.ids.BusinessSupplierId;

@Entity
@Table(name = "business_suppliers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessSupplier {

    @EmbeddedId
    private BusinessSupplierId id;

    @Column(name = "disabled")
    private boolean disabled = false;
}

