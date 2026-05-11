package com.main.numOps.domain.customer;

import com.main.numOps.domain.customer.enums.StatusCustomer;
import com.main.numOps.domain.customer.enums.TypeDocument;
import com.main.numOps.domain.providers.ProviderModel;
import com.main.numOps.utils.DateUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "Customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String razao;

    private String document;

    @Enumerated(EnumType.STRING)
    private TypeDocument typeDocument;

    private String cep;

    private String logradouro;

    private String numeroEndereco;

    private String complemento;

    private String bairro;

    private String cidade;

    private String uf;

    @ManyToOne
    @JoinColumn(name = "provider", nullable = false)
    private ProviderModel provider;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private StatusCustomer status;

    @PrePersist
    private void onCreate(){
        this.createdAt = DateUtils.nowWithoutNanos();
        this.status = StatusCustomer.ACTIVE;
    }
}
