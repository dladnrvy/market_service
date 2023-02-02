package com.example.market_service.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Getter
@Table(name = "vegetables")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VegetableEntity implements Serializable {

    /** 야채가게 아이디 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="vegetable_id")
    private Long vegetableId;

    /** 야채명 */
    @Column(nullable = false)
    private String name;

    /** 가격 */
    @Column(nullable = false)
    private Integer price;

    /** 저장일 */
    @CreatedDate
    @Column(name="reg_dt")
    private LocalDateTime regDate;

    @Builder
    public VegetableEntity(Long vegetableId, String name, Integer price, LocalDateTime regDate) {
        this.vegetableId = vegetableId;
        this.name = name;
        this.price = price;
        this.regDate = regDate;
    }
}
