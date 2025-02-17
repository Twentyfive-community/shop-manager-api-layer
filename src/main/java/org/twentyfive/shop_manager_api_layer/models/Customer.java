package org.twentyfive.shop_manager_api_layer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jdk.jfr.Registered;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name ="customers",
        uniqueConstraints = @UniqueConstraint(columnNames = {"company_name", "business_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name ="company_name", nullable = false)
    private String companyName;

    @Column(name="registered_office")
    private String registeredOffice;

    @Column(name="vat_number")
    private String vatNumber;

    @Column(name="pec")
    private String pec;

    @Column(name="email")
    private String email;

    @Column(name="sdi")
    private String sdi;

    @Column(name = "disabled")
    private boolean disabled = false;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    @ToString.Exclude
    private Set<CustomerExpense> expenses;
}
