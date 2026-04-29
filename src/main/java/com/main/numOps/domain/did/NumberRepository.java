package com.main.numOps.domain.did;

import com.main.numOps.Enuns.StatusNumber;
import com.main.numOps.domain.providers.ProviderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NumberRepository extends JpaRepository<NumberModel,Integer> {

    Page<NumberModel> findByProvider(ProviderModel provider, Pageable pageable);

    @Query("""
    SELECT n FROM numbers n
    WHERE (:status IS NULL OR n.statusNumber = :status)
      AND (:ufArea IS NULL OR n.ufArea = :ufArea)
      AND (:area IS NULL OR n.area = :area)
    ORDER BY function('RANDOM')
""")
    Page<NumberModel> findWithFilters(
            @Param("status") StatusNumber status,
            @Param("ufArea") String ufArea,
            @Param("area") String area,
            Pageable pageable
    );

}
