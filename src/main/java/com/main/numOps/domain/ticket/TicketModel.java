package com.main.numOps.domain.ticket;

import com.main.numOps.domain.providers.ProviderModel;
import com.main.numOps.domain.ticket.enuns.TicketStatus;
import com.main.numOps.domain.ticket.enuns.TicketType;
import com.main.numOps.domain.user.UserModel;
import com.main.numOps.utils.DateUtils;
import com.main.numOps.utils.TicketIdGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tickets")
public class TicketModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticket;

    private String fatura;

    @ManyToOne
    @JoinColumn(name = "usuario", nullable = false)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "provider", nullable = false)
    private ProviderModel provider;

    private LocalDateTime dateCreated;

    private LocalDateTime dateFinished;

    private String cancelBy;

    private LocalDateTime cancelAt;

    @Enumerated(EnumType.STRING)
    private TicketType type;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = DateUtils.nowWithoutNanos();
        this.status = TicketStatus.CREATED;

        if (this.id == null) {
            if (this.type == null) {
                throw new IllegalStateException("TicketType não pode ser nulo para gerar ID");
            }

            this.ticket = TicketIdGenerator.gerar(this.type.getPrefixo());
        }
    }
}

