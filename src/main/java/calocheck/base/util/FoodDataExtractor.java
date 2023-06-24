package calocheck.base.util;


import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.foodInfo.service.FoodInfoService;
import calocheck.boundedContext.nutritionInfo.entity.NutritionInfo;
import calocheck.boundedContext.nutritionInfo.service.NutritionInfoService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Configuration
@RequiredArgsConstructor
public class FoodDataExtractor {
    private final FoodInfoService foodInfoService;
    private final NutritionInfoService nutritionInfoService;

    Map<String, Integer> indexMap = new HashMap<>() {{
        put("식품명", 5);
        put("제조사/유통사", 7);
        put("식품군대분류", 8);
        put("1회제공량", 10);
        put("내용량_단위", 11);
        put("총내용량(g)", 12);
        put("총내용량(mL)", 13);
        put("에너지(kcal)", 14);
        put("단백질(g)", 16);
        put("지방(g)", 17);
        put("탄수화물(g)", 18);
        put("총당류(g)", 19);
        put("총식이섬유(g)", 24);
        put("칼슘(mg)", 25);
        put("철(mg)", 26);
        put("마그네슘(mg)", 27);
        put("칼륨(mg)", 29);
        put("나트륨(mg)", 30);
        put("콜레스테롤(g)", 73);
        put("포화지방산(g)", 75);
        put("트랜스지방산(g)", 84);
        put("불포화지방산(g)", 85);
    }};

    public void readFile() {
        String filePath = "src/main/java/calocheck/excel/data1.xlsx";
        try (FileInputStream fis = new FileInputStream(filePath)) {
            Workbook workbook;

            if (filePath.toLowerCase().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fis); // .xlsx 형식인 경우
            } else if (filePath.toLowerCase().endsWith(".xls")) {
                workbook = new HSSFWorkbook(fis); // .xls 형식인 경우
            } else {
                throw new IllegalArgumentException("지원하지 않는 파일 형식입니다.");
            }

            Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트를 가져옴

            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                String foodName = row.getCell(indexMap.get("식품명")).getStringCellValue();
                String manufacturer = row.getCell(indexMap.get("제조사/유통사")).getStringCellValue();
                String category = row.getCell(indexMap.get("식품군대분류")).getStringCellValue();
                int portionSize = Integer.parseInt(row.getCell(indexMap.get("1회제공량")).getStringCellValue());
                String unit = row.getCell(indexMap.get("내용량_단위")).getStringCellValue();
                String gramTotalSize = row.getCell(indexMap.get("총내용량(g)")).getStringCellValue();
                String literTotalSize = row.getCell(indexMap.get("총내용량(mL)")).getStringCellValue();
                int totalSize = 0;

                if (gramTotalSize.equals("-") && literTotalSize.equals("-")) {
                    totalSize = 0;
                } else {
                    if (gramTotalSize.equals("-"))
                        totalSize = (int)Double.parseDouble(literTotalSize);
                    else
                        totalSize = (int)Double.parseDouble(gramTotalSize);
                }

                String kcalStr = row.getCell(indexMap.get("에너지(kcal)")).getStringCellValue();
                double kcal = !Ut.check.isDouble(kcalStr) ? 0 : Double.parseDouble(kcalStr);
                String proteinStr = row.getCell(indexMap.get("단백질(g)")).getStringCellValue();
                double protein = !Ut.check.isDouble(proteinStr) ? 0 : Double.parseDouble(proteinStr);
                String fatStr = row.getCell(indexMap.get("지방(g)")).getStringCellValue();
                double fat = !Ut.check.isDouble(fatStr) ? 0 : Double.parseDouble(fatStr);
                String carbohydrateStr = row.getCell(indexMap.get("탄수화물(g)")).getStringCellValue();
                double carbohydrate = !Ut.check.isDouble(carbohydrateStr) ? 0 : Double.parseDouble(carbohydrateStr);
                String sugarStr = row.getCell(indexMap.get("총당류(g)")).getStringCellValue();
                double sugar = !Ut.check.isDouble(sugarStr) ? 0 : Double.parseDouble(sugarStr);
                String fiberStr = row.getCell(indexMap.get("총식이섬유(g)")).getStringCellValue();
                double fiber = !Ut.check.isDouble(fiberStr) ? 0 : Double.parseDouble(fiberStr);
                String calciumStr = row.getCell(indexMap.get("칼슘(mg)")).getStringCellValue();
                double calcium = !Ut.check.isDouble(calciumStr) ? 0 : Double.parseDouble(calciumStr);
                String ironStr = row.getCell(indexMap.get("철(mg)")).getStringCellValue();
                double iron = !Ut.check.isDouble(ironStr) ? 0 : Double.parseDouble(ironStr);
                String magnesiumStr = row.getCell(indexMap.get("마그네슘(mg)")).getStringCellValue();
                double magnesium = !Ut.check.isDouble(magnesiumStr) ? 0 : Double.parseDouble(magnesiumStr);
                String potassiumStr = row.getCell(indexMap.get("칼륨(mg)")).getStringCellValue();
                double potassium = !Ut.check.isDouble(potassiumStr) ? 0 : Double.parseDouble(potassiumStr);
                String sodiumStr = row.getCell(indexMap.get("나트륨(mg)")).getStringCellValue();
                double sodium = !Ut.check.isDouble(sodiumStr) ? 0 : Double.parseDouble(sodiumStr);
                String cholesterolStr = row.getCell(indexMap.get("콜레스테롤(g)")).getStringCellValue();
                double cholesterol = !Ut.check.isDouble(cholesterolStr) ? 0 : Double.parseDouble(cholesterolStr);
                String saturatedStr = row.getCell(indexMap.get("포화지방산(g)")).getStringCellValue();
                double saturated = !Ut.check.isDouble(saturatedStr) ? 0 : Double.parseDouble(saturatedStr);
                String transFattyAcidStr = row.getCell(indexMap.get("트랜스지방산(g)")).getStringCellValue();
                double transFattyAcid = !Ut.check.isDouble(transFattyAcidStr) ? 0 : Double.parseDouble(transFattyAcidStr);
                String unSaturatedStr = row.getCell(indexMap.get("불포화지방산(g)")).getStringCellValue();
                double unSaturated = !Ut.check.isDouble(unSaturatedStr) ? 0 : Double.parseDouble(unSaturatedStr);

                NutritionInfo nutritionInfo = nutritionInfoService.create(
                        kcal,
                        protein,
                        fat,
                        carbohydrate,
                        sugar,
                        fiber,
                        calcium,
                        iron,
                        magnesium,
                        potassium,
                        sodium,
                        cholesterol,
                        saturated,
                        transFattyAcid,
                        unSaturated
                );

                FoodInfo foodInfo = foodInfoService.create(
                        nutritionInfo,
                        foodName,
                        manufacturer,
                        category,
                        portionSize,
                        unit,
                        totalSize
                );
            }

            workbook.close(); // 메모리 해제
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
