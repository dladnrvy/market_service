package com.example.market_service.repository;


import com.example.market_service.domain.VegetableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VegetableRepository extends JpaRepository<VegetableEntity, Long> {
}
