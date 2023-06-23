package calocheck.boundedContext.recommend.entity;

import calocheck.boundedContext.fooditem.entity.FoodInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder(toBuilder = true)
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Recommend {

    @Id
    private long id;

    private String description;

    @OneToMany
    private List<FoodInfo> foodList;
}
