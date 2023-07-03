package calocheck.boundedContext.criteria.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Criteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gender;      //성별
    private int age;            //연령
    private int kcal;           //에너지 => fixme 계산
    private int carbohydrate;   //탄수화물 => fixme 계산
    private int protein;        //단백질
    private int fat;            //지방 => fixme 계산
    private int fiber;          //식이섬유
    private int calcium;        //칼슘
    private int sodium;         //나트륨
    private int potassium;      //칼륨
    private int magnesium;      //마그네슘

}
