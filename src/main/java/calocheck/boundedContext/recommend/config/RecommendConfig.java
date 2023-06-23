package calocheck.boundedContext.recommend.config;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecommendConfig {

    @Getter
    private static String[] carbohydrateFoodList; //탄수화물

    @Value("${custom.recommendList.carbohydrate}")
    public void setCarbohydrateFoodList(String[] carbohydrateFoodList){
        RecommendConfig.carbohydrateFoodList = carbohydrateFoodList;
    }

    @Getter
    private static String[] proteinFoodList; //단백질

    @Value("${custom.recommendList.protein}")
    public void setProteinFoodList(String[] proteinFoodList) {
        RecommendConfig.proteinFoodList = proteinFoodList;
    }

    @Getter
    private static String[] fatFoodList; //지방

    @Value("${custom.recommendList.fat}")
    public void setFatFoodList(String[] fatFoodList) {
        RecommendConfig.fatFoodList = fatFoodList;
    }

    @Getter
    private static String[] calciumFoodList; //칼슘

    @Value("${custom.recommendList.calcium}")
    public void setCalciumFoodList(String[] calciumFoodList) {
        RecommendConfig.calciumFoodList = calciumFoodList;
    }

    @Getter
    private static String[] sodiumFoodList; //나트륨

    @Value("${custom.recommendList.sodium}")
    public void setSodiumFoodList(String[] sodiumFoodList) {
        RecommendConfig.sodiumFoodList = sodiumFoodList;
    }

    @Getter
    private static String[] potassiumFoodList; //칼륨

    @Value("${custom.recommendList.potassium}")
    public void setPotassiumFoodList(String[] potassiumFoodList) {
        RecommendConfig.potassiumFoodList = potassiumFoodList;
    }

    @Getter
    private static String[] vitaminAFoodList; //비타민 A

    @Value("${custom.recommendList.vitaminA}")
    public void setVitaminAFoodList(String[] vitaminAFoodList) {
        RecommendConfig.vitaminAFoodList = vitaminAFoodList;
    }

    @Getter
    private static String[] vitaminCFoodList; //비타민 C

    @Value("${custom.recommendList.vitaminC}")
    public void setVitaminCFoodList(String[] vitaminCFoodList) {
        RecommendConfig.vitaminCFoodList = vitaminCFoodList;
    }

    @Getter
    private static String carbohydrateDescription;

    @Value("${custom.description.carbohydrate}")
    public void setCarbohydrateDescription(String carbohydrateDescription){
        RecommendConfig.carbohydrateDescription = carbohydrateDescription;
    }

    @Getter
    private static String proteinDescription;

    @Value("${custom.description.protein}")
    public void setProteinDescription(String proteinDescription){
        RecommendConfig.proteinDescription = proteinDescription;
    }

    @Getter
    private static String fatDescription;

    @Value("${custom.description.fat}")
    public void setFatDescription(String fatDescription){
        RecommendConfig.fatDescription = fatDescription;
    }

    @Getter
    private static String calciumDescription;

    @Value("${custom.description.calcium}")
    public void setCalciumDescription(String calciumDescription){
        RecommendConfig.calciumDescription = calciumDescription;
    }

    @Getter
    private static String sodiumDescription;

    @Value("${custom.description.sodium}")
    public void setSodiumDescription(String sodiumDescription){
        RecommendConfig.sodiumDescription = sodiumDescription;
    }

    @Getter
    private static String potassiumDescription;

    @Value("${custom.description.potassium}")
    public void setPotassiumDescription(String potassiumDescription){
        RecommendConfig.potassiumDescription = potassiumDescription;
    }

    @Getter
    private static String vitaminADescription;

    @Value("${custom.description.vitaminA}")
    public void setVitaminADescription(String vitaminADescription){
        RecommendConfig.vitaminADescription = vitaminADescription;
    }

    @Getter
    private static String vitaminCDescription;

    @Value("${custom.description.vitaminC}")
    public void setVitaminCDescription(String vitaminCDescription){
        RecommendConfig.vitaminCDescription = vitaminCDescription;
    }


}
