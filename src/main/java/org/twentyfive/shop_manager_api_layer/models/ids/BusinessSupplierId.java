package org.twentyfive.shop_manager_api_layer.models.ids;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.models.Business;
import org.twentyfive.shop_manager_api_layer.models.Supplier;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessSupplierId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "business_id",nullable = false)
    private Business business;

    @ManyToOne
    @JoinColumn(name = "supplier_id",nullable = false)
    private Supplier supplier;
}
