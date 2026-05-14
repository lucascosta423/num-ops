package com.main.numOps.domain.didAvailable;

import com.main.numOps.domain.didAvailable.enums.DidAvailableStatus;
import com.main.numOps.utils.DateUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "did_available")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DidAvailableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 2)
    private String cn;

    @Column(nullable = false,length = 4)
    private String prefixo;

    @Column(nullable = false,length = 4,unique = true)
    private String mcdu;

    @Column(nullable = false,length = 20)
    private String area;

    private String ufArea;

    @Column(name = "upload_at")
    private LocalDateTime uploadAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private DidAvailableStatus status;

    public String getNumero(){
        return this.cn + this.prefixo + this.mcdu;
    }

    @PreUpdate
    public void beforeUpdate() {
        this.updatedAt = DateUtils.nowWithoutNanos();
    }

    @PrePersist
    public void beforeInsert(){
        this.uploadAt = DateUtils.nowWithoutNanos();
    }
}
