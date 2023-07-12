package calocheck.base.util.excel.service;

import calocheck.base.util.Ut;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.foodInfo.service.FoodInfoService;
import calocheck.boundedContext.nutrient.entity.Nutrient;
import calocheck.boundedContext.nutrient.service.NutrientService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExcelService {
    private final FoodInfoService foodInfoService;
    private final NutrientService nutrientService;
    private final int BATCH_SIZE = 1000;

    private Map<String, Integer> foodInfoMap = new HashMap<>() {{
        put("식품코드", 2);
        put("식품명", 5);
        put("제조사/유통사", 7);
        put("식품군대분류", 8);
        put("1회제공량", 10);
        put("내용량_단위", 11);
        put("총내용량(g)", 12);
        put("총내용량(mL)", 13);
        put("에너지(kcal)", 14);
    }};
    private Map<String, Integer> nutrientGramMap = new HashMap<>() {{
        put("단백질", 16);
        put("지방", 17);
        put("탄수화물", 18);
        put("총당류", 19);
        put("총식이섬유", 24);
        put("콜레스테롤", 73);
        put("포화지방산", 75);
        put("트랜스지방산", 84);
        put("불포화지방산", 85);
    }};

    private Map<String, Integer> nutrientMiliGramMap = new HashMap<>() {{
        put("칼슘", 25);
        put("철", 26);
        put("마그네슘", 27);
        put("칼륨", 29);
        put("나트륨", 30);
    }};

    public String cellReader(Cell cell) throws Exception {
        switch (cell.getCellType()) {
            case _NONE:
            case BLANK:
                return cell.getStringCellValue() + "";
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return cell.getNumericCellValue() + "";
            case FORMULA:
                return cell.getCellFormula() + "";
            case BOOLEAN:
                return cell.getBooleanCellValue() + "";
            case ERROR:
                throw new Exception("엑셀 파일 에러");
            default:
                return "";
        }
    }

    public void processExcel(InputStream inputStream) {
        Workbook workbook = null;

        try {
            workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트를 가져옴

            List<FoodInfo> foodInfos = new ArrayList<>();

            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                try {
                    foodInfos.add(extractFoodInfo(row));
                } catch (Exception e) {
                }


                if (foodInfos.size() == BATCH_SIZE) {
                    List<Nutrient> nutrients = new ArrayList<>();

                    foodInfoService.saveAll(foodInfos);
                    foodInfos.stream().forEach(foodInfo -> nutrients.addAll(foodInfo.getNutrientList()));

                    nutrientService.saveAll(nutrients);
                    foodInfos.clear();
                    System.out.println(BATCH_SIZE + " Save Complete");
                }
            }

            List<Nutrient> nutrients = new ArrayList<>();

            foodInfoService.saveAll(foodInfos);
            foodInfos.stream().forEach(foodInfo -> nutrients.addAll(foodInfo.getNutrientList()));
            nutrientService.saveAll(nutrients);

            workbook.close(); // 메모리 해제
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FoodInfo extractFoodInfo(Row row) throws Exception {
        String foodCode = cellReader(row.getCell(foodInfoMap.get("식품코드")));
        String foodName = cellReader(row.getCell(foodInfoMap.get("식품명")));
        String manufacturer = cellReader(row.getCell(foodInfoMap.get("제조사/유통사")));
        String category = cellReader(row.getCell(foodInfoMap.get("식품군대분류")));
        String portionSizeStr = cellReader(row.getCell(foodInfoMap.get("1회제공량")));
        String unit = cellReader(row.getCell(foodInfoMap.get("내용량_단위")));
        String gramTotalSize = cellReader(row.getCell(foodInfoMap.get("총내용량(g)")));
        String literTotalSize = cellReader(row.getCell(foodInfoMap.get("총내용량(mL)")));
        String kcalStr = cellReader(row.getCell(foodInfoMap.get("에너지(kcal)")));
        
        int portionSize = !Ut.check.isInteger(portionSizeStr) ? 0 : Integer.parseInt(portionSizeStr);
        double kcal = !Ut.check.isDouble(kcalStr) ? 0 : Double.parseDouble(kcalStr);

        int totalSize = 0;

        if (gramTotalSize.equals("-") && literTotalSize.equals("-")) {
            totalSize = 0;
        } else {
            if (gramTotalSize.equals("-"))
                totalSize = (int) Double.parseDouble(literTotalSize);
            else
                totalSize = (int) Double.parseDouble(gramTotalSize);
        }

        FoodInfo foodInfo = FoodInfo.builder()
                .foodCode(foodCode)
                .foodName(foodName)
                .manufacturer(manufacturer)
                .category(category)
                .portionSize(portionSize)
                .unit(unit)
                .totalSize(totalSize)
                .kcal(kcal)
                .build();

        extractNurientList(row, foodInfo);

        return foodInfo;
    }

    public void extractNurientList(Row row, FoodInfo foodInfo) {
        for (String key : nutrientGramMap.keySet()) {
            int value = nutrientGramMap.get(key);
            String str = row.getCell(value).getStringCellValue();
            double nutrientVal = !Ut.check.isDouble(str) ? 0 : Double.parseDouble(str);

            Nutrient nutrient = Nutrient.builder()
                    .foodInfo(foodInfo)
                    .name(key)
                    .value(nutrientVal)
                    .build();

            foodInfo.addNutrient(nutrient);
        }

        for (String key : nutrientMiliGramMap.keySet()) {
            int value = nutrientMiliGramMap.get(key);
            String str = row.getCell(value).getStringCellValue();
            double nutrientVal = !Ut.check.isDouble(str) ? 0 : Double.parseDouble(str) / 1000;

            Nutrient nutrient = Nutrient.builder()
                    .foodInfo(foodInfo)
                    .name(key)
                    .value(nutrientVal)
                    .build();

            foodInfo.addNutrient(nutrient);
        }
    }
}