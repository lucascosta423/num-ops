package com.main.numOps.domain.Number.portability;


import com.main.numOps.Enuns.StatusPortability;
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
    private Integer codigoCnl;

    @Column(nullable = false)
    private String nomePrestadora;

    @Column(nullable = false)
    private String nomeLocalidade;

    @Column(nullable = false)
    private String areaLocal;

    private String dataAgendamento;

    private String horaAgendamento;

    private StatusPortability statusSolicitacao;

}
