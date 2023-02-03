package com.example.market_service.dto;

import com.example.market_service.domain.VegetableEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetVegetableNameAndPriceDto {

    /** 야채명 */
    private String name;
    /** 가격 */
    private Integer price;

    public GetVegetableNameAndPriceDto(VegetableEntity vegetableEntity) {
        this.name = vegetableEntity.getName();
        this.price = vegetableEntity.getPrice();
    }
}
