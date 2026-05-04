package com.main.numOps.domain.did;

import com.main.numOps.Enuns.DidStatus;
import com.main.numOps.Enuns.StatusNumber;
import com.main.numOps.domain.didAvailable.DidAvailableModel;
import com.main.numOps.domain.providers.ProviderModel;
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
    private Integer id;

    private String razao;

    private String documento;

    private String cep;

    private String logradouro;

    private String numeroEndereco;

    private String complemento;

    private String bairro;

    private String cidade;

    private String uf;

    @ManyToOne
    @JoinColumn(name = "did",nullable = false)
    private DidAvailableModel did;

    @ManyToOne
    @JoinColumn(name = "provider", nullable = false)
    private ProviderModel provider;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private DidStatus status;

    @PrePersist
    private void onCreate(){
        this.createdAt = DateUtils.nowWithoutNanos();
    }

}
