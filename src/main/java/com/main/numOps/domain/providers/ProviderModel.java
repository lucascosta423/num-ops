package com.main.numOps.domain.providers;

import com.main.numOps.Enuns.Status;
import com.main.numOps.utils.DateUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity(name = "Provider")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProviderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,length = 150)
    private String nome;

    @Column(nullable = false,unique = true,length = 11)
    private String contato;

    @Column(nullable = false,unique = true,length = 15)
    private String document;

    @Column(nullable = false,unique = true,length = 70)
    private String email;

    private LocalDateTime dataCriado;

    private LocalDateTime dataAtualizacao;

    @Enumerated(EnumType.STRING)
    private Status status;

    public boolean verifyStatus(){
        return this.status == Status.ACTIVE;
    }

    @PrePersist
    protected void onCreate() {
        this.dataCriado = DateUtils.nowWithoutNanos();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = DateUtils.nowWithoutNanos();
    }
}
