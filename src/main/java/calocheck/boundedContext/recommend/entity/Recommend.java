package calocheck.boundedContext.recommend.entity;

import calocheck.boundedContext.fooditem.entity.FoodInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@SuperBuilder(toBuilder = true)
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Recommend {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String nutritionName;
    private String description;
    @OneToMany
    private List<FoodInfo> foodList;
}
