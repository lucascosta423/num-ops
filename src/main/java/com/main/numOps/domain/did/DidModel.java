package com.main.numOps.domain.did;

import com.main.numOps.domain.did.enums.DidStatus;
import com.main.numOps.domain.customer.CustomerModel;
import com.main.numOps.domain.didAvailable.DidAvailableModel;
import com.main.numOps.utils.DateUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "did")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DidModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "did",nullable = false)
    private DidAvailableModel did;

    @ManyToOne
    @JoinColumn(name = "customer",nullable = false)
    private CustomerModel customer;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private DidStatus status;

    @PrePersist
    private void onCreate(){
        this.createdAt = DateUtils.nowWithoutNanos();
    }

}
