package com.main.numOps.domain.Number.portability;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PortabilityRepository extends JpaRepository<PortabilityModel, Integer> {
}
