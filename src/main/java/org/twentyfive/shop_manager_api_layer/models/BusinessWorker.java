package org.twentyfive.shop_manager_api_layer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.twentyfive.shop_manager_api_layer.models.ids.BusinessWorkerId;
import org.twentyfive.shop_manager_api_layer.utilities.classes.enums.Role;

import java.util.List;

@Entity
@Table(name = "business_workers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessWorker {
    @EmbeddedId
    private BusinessWorkerId id; // ID della relazione

    @Column(name = "role",nullable = false)
    private Role role;
    @Column(name ="disabled")
    private boolean disabled;
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "worker")
    private List<Expense> expenses;

    public BusinessWorker(BusinessWorkerId businessWorkerId, String role) {
        this.id = businessWorkerId;
        this.role = Role.fromString(role);
    }
}
