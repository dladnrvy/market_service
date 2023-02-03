package com.example.market_service.dto;

import com.example.market_service.domain.FruitEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetFruitNameAndPriceDto {

    /** 과일명 */
    private String name;
    /** 가격 */
    private Integer price;

    public GetFruitNameAndPriceDto(FruitEntity fruitEntity) {
        this.name = fruitEntity.getName();
        this.price = fruitEntity.getPrice();
    }
}
