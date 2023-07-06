package calocheck.boundedContext.tag.config;

import calocheck.boundedContext.photo.config.S3Config;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TagConfig {

    @Getter
    private static int carbohydrateCriteria;

    @Value("${custom.tag.criteria.carbohydrate}")
    public void setCarbohydrateCriteria(int carbohydrateCriteria) {
        TagConfig.carbohydrateCriteria = carbohydrateCriteria;
    }

    @Getter
    private static String carbohydrateColor;

    @Value("${custom.tag.color.carbohydrate}")
    public void setCarbohydrateColor(String carbohydrateColor) {
        TagConfig.carbohydrateColor = carbohydrateColor;
    }

    @Getter
    private static int fatCriteria;

    @Value("${custom.tag.criteria.fat}")
    public void setFatCriteria(int fatCriteria) {
        TagConfig.fatCriteria = fatCriteria;
    }

    @Getter
    private static String fatColor;

    @Value("${custom.tag.color.fat}")
    public void setFatColor(String fatColor) {
        TagConfig.fatColor = fatColor;
    }

    @Getter
    private static int proteinCriteria;

    @Value("${custom.tag.criteria.protein}")
    public void setProteinCriteria(int proteinCriteria) {
        TagConfig.proteinCriteria = proteinCriteria;
    }

    @Getter
    private static String proteinColor;

    @Value("${custom.tag.color.protein}")
    public void setProteinColor(String proteinColor) {
        TagConfig.proteinColor = proteinColor;
    }

    @Getter
    private static int calciumCriteria;

    @Value("${custom.tag.criteria.calcium}")
    public void setCalciumCriteria(int calciumCriteria) {
        TagConfig.calciumCriteria = calciumCriteria;
    }

    @Getter
    private static String calciumColor;

    @Value("${custom.tag.color.calcium}")
    public void setCalciumColor(String calciumColor) {
        TagConfig.calciumColor = calciumColor;
    }

    @Getter
    private static int sodiumCriteria;

    @Value("${custom.tag.criteria.sodium}")
    public void setSodiumCriteria(int sodiumCriteria) {
        TagConfig.sodiumCriteria = sodiumCriteria;
    }

    @Getter
    private static String sodiumColor;

    @Value("${custom.tag.color.sodium}")
    public void setSodiumColor(String sodiumColor) {
        TagConfig.sodiumColor = sodiumColor;
    }

    @Getter
    private static int potassiumCriteria;

    @Value("${custom.tag.criteria.potassium}")
    public void setPotassiumCriteria(int potassiumCriteria) {
        TagConfig.potassiumCriteria = potassiumCriteria;
    }

    @Getter
    private static String potassiumColor;

    @Value("${custom.tag.color.potassium}")
    public void setPotassiumColor(String potassiumColor) {
        TagConfig.potassiumColor = potassiumColor;
    }

    @Getter
    private static int magnesiumCriteria;

    @Value("${custom.tag.criteria.magnesium}")
    public void setMagnesiumCriteria(int magnesiumCriteria) {
        TagConfig.magnesiumCriteria = magnesiumCriteria;
    }

    @Getter
    private static String magnesiumColor;

    @Value("${custom.tag.color.magnesium}")
    public void setMagnesiumColor(String magnesiumColor) {
        TagConfig.magnesiumColor = magnesiumColor;
    }

}
