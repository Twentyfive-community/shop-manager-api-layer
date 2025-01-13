package org.twentyfive.shop_manager_api_layer.models.ids;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.twentyfive.shop_manager_api_layer.models.Business;
import org.twentyfive.shop_manager_api_layer.models.Worker;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BusinessWorkerId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business; // Riferimento al Business

    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker; // Riferimento al Worker
}
