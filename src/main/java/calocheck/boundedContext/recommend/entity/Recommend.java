package calocheck.boundedContext.recommend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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

    @ElementCollection
    private String[] foodList;      //FIXME 동작은 잘 되는 것 같은데, 빨간줄 그여짐

}
