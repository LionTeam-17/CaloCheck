//package calocheck.boundedContext.recommend.config;
//
//
//import lombok.Getter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RecommendConfig {
//
//    @Getter
//    private static String[] carbohydrateFoodList; //탄수화물
//
//    @Value("${custom.recommendList.carbohydrate}")
//    public void setCarbohydrateFoodList(String[] carbohydrateFoodList){
//        RecommendConfig.carbohydrateFoodList = carbohydrateFoodList;
//    }
//
//    @Getter
//    private static String[] proteinFoodList; //단백질
//
//    @Value("${custom.recommendList.protein}")
//    public void setProteinFoodList(String[] proteinFoodList) {
//        RecommendConfig.proteinFoodList = proteinFoodList;
//    }
//
//    @Getter
//    private static String[] fatFoodList; //지방
//
//    @Value("${custom.recommendList.fat}")
//    public void setFatFoodList(String[] fatFoodList) {
//        RecommendConfig.fatFoodList = fatFoodList;
//    }
//
//    @Getter
//    private static String[] calciumFoodList; //칼슘
//
//    @Value("${custom.recommendList.calcium}")
//    public void setCalciumFoodList(String[] calciumFoodList) {
//        RecommendConfig.calciumFoodList = calciumFoodList;
//    }
//
//    @Getter
//    private static String[] sodiumFoodList; //나트륨
//
//    @Value("${custom.recommendList.sodium}")
//    public void setSodiumFoodList(String[] sodiumFoodList) {
//        RecommendConfig.sodiumFoodList = sodiumFoodList;
//    }
//
//    @Getter
//    private static String[] potassiumFoodList; //칼륨
//
//    @Value("${custom.recommendList.potassium}")
//    public void setPotassiumFoodList(String[] potassiumFoodList) {
//        RecommendConfig.potassiumFoodList = potassiumFoodList;
//    }
//
//    @Getter
//    private static String[] vitaminAFoodList; //비타민 A
//
//    @Value("${custom.recommendList.vitaminA}")
//    public void setVitaminAFoodList(String[] vitaminAFoodList) {
//        RecommendConfig.vitaminAFoodList = vitaminAFoodList;
//    }
//
//    @Getter
//    private static String[] vitaminCFoodList; //비타민 C
//
//    @Value("${custom.recommendList.vitaminC}")
//    public void setVitaminCFoodList(String[] vitaminCFoodList) {
//        RecommendConfig.vitaminCFoodList = vitaminCFoodList;
//    }
//
//    @Getter
//    private static String[] meatFoodList; //저지방&고단백 육류
//
//    @Value("${custom.recommendList.meat}")
//    public void setMeatFoodList(String[] meatFoodList) {
//        RecommendConfig.meatFoodList = meatFoodList;
//    }
//
//    @Getter
//    private static String[] highGIFoodList; //GI지수 높은 음식
//
//    @Value("${custom.recommendList.highGI}")
//    public void setHighGIFoodList(String[] highGIFoodList) {
//        RecommendConfig.highGIFoodList = highGIFoodList;
//    }
//
//    @Getter
//    private static String[] lowGIFoodList; //GI지수 낮은 음식
//
//    @Value("${custom.recommendList.lowGI}")
//    public void setLowGIFoodList(String[] lowGIFoodList) {
//        RecommendConfig.lowGIFoodList = lowGIFoodList;
//    }
//
//    @Getter
//    private static String carbohydrateDescription;
//
//    @Value("${custom.description.carbohydrate}")
//    public void setCarbohydrateDescription(String carbohydrateDescription){
//        RecommendConfig.carbohydrateDescription = carbohydrateDescription;
//    }
//
//    @Getter
//    private static String proteinDescription;
//
//    @Value("${custom.description.protein}")
//    public void setProteinDescription(String proteinDescription){
//        RecommendConfig.proteinDescription = proteinDescription;
//    }
//
//    @Getter
//    private static String fatDescription;
//
//    @Value("${custom.description.fat}")
//    public void setFatDescription(String fatDescription){
//        RecommendConfig.fatDescription = fatDescription;
//    }
//
//    @Getter
//    private static String calciumDescription;
//
//    @Value("${custom.description.calcium}")
//    public void setCalciumDescription(String calciumDescription){
//        RecommendConfig.calciumDescription = calciumDescription;
//    }
//
//    @Getter
//    private static String sodiumDescription;
//
//    @Value("${custom.description.sodium}")
//    public void setSodiumDescription(String sodiumDescription){
//        RecommendConfig.sodiumDescription = sodiumDescription;
//    }
//
//    @Getter
//    private static String potassiumDescription;
//
//    @Value("${custom.description.potassium}")
//    public void setPotassiumDescription(String potassiumDescription){
//        RecommendConfig.potassiumDescription = potassiumDescription;
//    }
//
//    @Getter
//    private static String vitaminADescription;
//
//    @Value("${custom.description.vitaminA}")
//    public void setVitaminADescription(String vitaminADescription){
//        RecommendConfig.vitaminADescription = vitaminADescription;
//    }
//
//    @Getter
//    private static String vitaminCDescription;
//
//    @Value("${custom.description.vitaminC}")
//    public void setVitaminCDescription(String vitaminCDescription){
//        RecommendConfig.vitaminCDescription = vitaminCDescription;
//    }
//
//    @Getter
//    private static String meatDescription;
//
//    @Value("${custom.description.meat}")
//    public void setMeatDescription(String meatDescription){
//        RecommendConfig.meatDescription = meatDescription;
//    }
//
//    @Getter
//    private static String highGIDescription;
//
//    @Value("${custom.description.highGI}")
//    public void setHighGIDescription(String highGIDescription){
//        RecommendConfig.highGIDescription = highGIDescription;
//    }
//
//    @Getter
//    private static String lowGIDescription;
//
//    @Value("${custom.description.lowGI}")
//    public void setLowGIDescription(String lowGIDescription){
//        RecommendConfig.lowGIDescription = lowGIDescription;
//    }
//
//    public static String getDescription(String nutrition) {
//        switch (nutrition) {
//            case "탄수화물":
//                return carbohydrateDescription;
//            case "단백질":
//                return proteinDescription;
//            case "지방":
//                return fatDescription;
//            case "칼슘":
//                return calciumDescription;
//            case "나트륨":
//                return sodiumDescription;
//            case "칼륨":
//                return potassiumDescription;
//            case "비타민A":
//                return vitaminADescription;
//            case "비타민C":
//                return vitaminCDescription;
//            case "고단백&저지방":
//                return meatDescription;
//            case "GI지수 높은 음식":
//                return highGIDescription;
//            case "GI지수 낮은 음식":
//                return lowGIDescription;
//        }
//        return null;
//    }
//
//    public static String[] getFoodList(String nutrition) {
//        switch (nutrition) {
//            case "탄수화물":
//                return carbohydrateFoodList;
//            case "단백질":
//                return proteinFoodList;
//            case "지방":
//                return fatFoodList;
//            case "칼슘":
//                return calciumFoodList;
//            case "나트륨":
//                return sodiumFoodList;
//            case "칼륨":
//                return potassiumFoodList;
//            case "비타민A":
//                return vitaminAFoodList;
//            case "비타민C":
//                return vitaminCFoodList;
//            case "고단백&저지방":
//                return meatFoodList;
//            case "GI지수 높은 음식":
//                return lowGIFoodList;
//            case "GI지수 낮은 음식":
//                return highGIFoodList;
//        }
//        return null;
//    }
//}
