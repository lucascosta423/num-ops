package com.main.numOps.domain.operators;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperatorsRepository extends JpaRepository<OperatorsModel,Integer> {

    @Query("SELECT b FROM operators b WHERE " +
            "b.prefixo = :prefixo " +
            "AND :mcdu BETWEEN b.faixaInicial AND b.faixaFinal " +
            "AND b.codigoNacional = :codigoNacional")
    Optional<OperatorsModel> findByNumero(
            @Param("codigoNacional") String codigoNacional,
            @Param("prefixo") String prefixo,
            @Param("mcdu") String mcdu
    );

    @Query(value = "SELECT o.codigo_cnl FROM operators o WHERE o.nome_localidade = :areaLocal LIMIT 1",
            nativeQuery = true)
    Optional<Integer> findCnlAreaLocal(
            @Param("areaLocal") String areaLocal
    );

}

