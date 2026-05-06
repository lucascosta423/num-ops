package com.main.numOps.domain.didAvailable;

import com.main.numOps.Enuns.DidStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DidAvailableRepository extends JpaRepository<DidAvailableModel, Integer> {

    @Query("""
                SELECT da FROM did_available da
                WHERE (:status IS NULL OR da.status = :status)
                  AND (:ufArea IS NULL OR da.ufArea = :ufArea)
                  AND (:area IS NULL OR da.area = :area)
                ORDER BY function('RANDOM')
            """)
    Page<DidAvailableModel> findWithFilters(
            @Param("status") DidStatus status,
            @Param("ufArea") String ufArea,
            @Param("area") String area,
            Pageable pageable
    );

    @Modifying
    @Transactional
    @Query("""
            UPDATE did_available da
            SET da.status = COALESCE(:status, da.status),
            da.updatedAt = COALESCE(:updateAt, da.updatedAt)
            WHERE da.id IN :ids
            """
    )
    Integer updateStatusDidAvailable(
            @Param("ids") List<Integer> ids,
            @Param("status") DidStatus status,
            @Param("updateAt")LocalDateTime updateAt
            );

    @Modifying
    @Transactional
    @Query("""
        UPDATE did_available da
        SET  da.status = COALESCE(:status, da.status),
            da.updatedAt = COALESCE(:updatedAt, da.updatedAt)
        WHERE
           da.cn = :cn
        AND
           da.prefixo = :prefixo
        AND
           da.mcdu BETWEEN :start AND :end
        """)
    int updateByRange(
            @Param("cn") String cn,
            @Param("prefixo")String prefixo,
            @Param("start") String start,
            @Param("end") String end,
            @Param("status") DidStatus status,
            @Param("updatedAt") LocalDateTime updatedAt
    );

    @Query("""
            SELECT
                da
            FROM
                did_available da
            WHERE
               da.cn = :cn
            AND
               da.prefixo = :prefixo
            AND
               da.mcdu BETWEEN :start AND :end
            """)
    Page<DidAvailableModel> listByRange(
            @Param("cn") String cn,
            @Param("prefixo")String prefixo,
            @Param("start") String start,
            @Param("end") String end,
            Pageable pageable
    );
}
