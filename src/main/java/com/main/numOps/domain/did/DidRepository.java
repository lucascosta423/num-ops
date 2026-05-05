package com.main.numOps.domain.did;

import com.main.numOps.domain.didAvailable.DidAvailableModel;
import com.main.numOps.domain.providers.ProviderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DidRepository extends JpaRepository<DidModel,Integer> {

    Page<DidModel> findByProvider(ProviderModel provider, Pageable pageable);

    @Query("""
    SELECT d.did
    FROM did d
    WHERE d.document = :document
""")
    Page<DidAvailableModel> findDidDocument(
            @Param("document") String document,
            Pageable pageable
    );

    @Query(value = """
        SELECT DISTINCT ON (d.document) d.*
        FROM did d
        ORDER BY d.document, d.id
    """, nativeQuery = true)
    Page<DidModel> findDistinctAll(Pageable pageable);

    @Query(value = """
        SELECT DISTINCT ON (d.document) d.*
        FROM did d
        WHERE d.provider = :provider
        ORDER BY d.document, d.id
    """, nativeQuery = true)
    Page<DidModel> findDistincByProvider(
            Pageable pageable,
            @Param("provider") ProviderModel provider);
}
