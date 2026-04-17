package com.main.numOps.domain.Number.portability;


import com.main.numOps.Enuns.StatusPortability;
import com.main.numOps.domain.providers.ProviderModel;
import com.main.numOps.domain.ticket.TicketModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "portability")
public class PortabilityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private String municipio;

    @Column(nullable = false)
    private Integer cnlMunicipo;

    @Column(nullable = false)
    private String areaLocal;

    @Column(nullable = false)
    private Integer cnlAreLocal;

    @ManyToOne
    @JoinColumn(name = "provedor_id", nullable = false)
    private ProviderModel provider;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private TicketModel ticket;

    private String dataAgendamento;

    private String horaAgendamento;

    @Enumerated(EnumType.STRING)
    private StatusPortability status;

}
