package com.example.market_service.repository;

import com.example.market_service.domain.FruitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FruitRepository extends JpaRepository<FruitEntity, Long> {

    /** 전체 과일명 */
    @Query(
            "select r.name from FruitEntity r"
    )
    List<String> findNameAll();

    /** 해당하는 과일조회 */
    @Query(
            "select r from FruitEntity r where r.name = :findName"
    )
    List<FruitEntity> findNameAndPriceByName(@Param("findName") String name);
}
