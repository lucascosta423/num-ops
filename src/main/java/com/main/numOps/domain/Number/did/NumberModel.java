package com.main.numOps.domain.Number.did;

import com.main.numOps.Enuns.StatusNumber;
import com.main.numOps.domain.ticket.TicketModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "numbers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NumberModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,length = 2)
    private String cn;

    @Column(nullable = false,length = 4)
    private String prefixo;

    @Column(nullable = false,length = 4)
    private String mcdu;

    @Column(nullable = false,length = 20)
    private String area;

    private String ufArea;

    private LocalDateTime dateCreated;

    private LocalDateTime dateFinished;

    private LocalDateTime dataUpload;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private TicketModel ticket;

    @Enumerated(EnumType.STRING)
    private StatusNumber statusNumber;

    @PrePersist
    private void onCreate(){
        this.dataUpload = LocalDateTime.now();
    }


    public String getNumero(){
        return this.cn + this.prefixo + this.mcdu;
    }
}
