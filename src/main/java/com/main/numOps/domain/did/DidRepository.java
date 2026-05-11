package com.main.numOps.domain.did;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DidRepository extends JpaRepository<DidModel,Long> {

}
