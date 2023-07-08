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
    private int carbohydrate;   //탄수화물
    private int protein;        //단백질
    private int fat;            //지방
    private int fiber;          //식이섬유
    private int calcium;        //칼슘
    private int sodium;         //나트륨
    private int potassium;      //칼륨
    private int magnesium;      //마그네슘

    public int getCriteria(String name){

        switch (name) {
            case ("탄수화물") -> {
                return this.carbohydrate;
            }
            case ("단백질") -> {
                return this.protein;
            }
            case ("지방") -> {
                return this.fat;
            }
            case ("식이섬유") -> {
                return this.fiber;
            }
            case ("칼슘") -> {
                return this.calcium;
            }
            case ("나트륨") -> {
                return this.sodium;
            }
            case ("칼륨") -> {
                return this.potassium;
            }
            case ("마그네슘") -> {
                return this.magnesium;
            }
            default -> {
                return 0;
            }
        }
    }

}
