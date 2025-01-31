package org.twentyfive.shop_manager_api_layer.listeners;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.twentyfive.shop_manager_api_layer.auditable.Auditable;

import java.time.LocalDateTime;

public class CustomAuditingListener {

    @PrePersist
    public void setCreatedAt(Auditable auditable) {
        auditable.setCreatedAt(LocalDateTime.now().plusHours(1));
        auditable.setUpdatedAt(LocalDateTime.now().plusHours(1));
    }

    @PreUpdate
    public void setUpdatedAt(Auditable auditable) {
        auditable.setUpdatedAt(LocalDateTime.now().plusHours(1));
    }
}
