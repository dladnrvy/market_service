package com.example.market_service.repository;


import com.example.market_service.domain.FruitEntity;
import com.example.market_service.domain.VegetableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VegetableRepository extends JpaRepository<VegetableEntity, Long> {

    /** 전체 야채명 */
    @Query(
            "select r.name from VegetableEntity r"
    )
    List<String> findNameAll();

    /** 해당하는 야채조회 */
    @Query(
            "select r from VegetableEntity r where r.name = :findName"
    )
    List<VegetableEntity> findNameAndPriceByName(@Param("findName") String name);
}
