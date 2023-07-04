package calocheck.base.util;


import calocheck.boundedContext.criteria.entity.Criteria;
import calocheck.boundedContext.criteria.service.CriteriaService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CriteriaDataExtractor {

    private final CriteriaService criteriaService;

    Map<String, Integer> indexMap = new HashMap<>() {{
            put("성별", 0);
            put("연령", 1);
            put("탄수화물", 3);
            put("식이섬유", 4);
            put("지방", 5);
            put("단백질", 6);
            put("칼슘", 7);
            put("나트륨",8);
            put("칼륨",9);
            put("마그네슘",10);
    }};

    public void readFile() {
        String filePath = "src/main/java/calocheck/excel/nutrientCriteria.xlsx";
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

                String gender = row.getCell(indexMap.get("성별")).getStringCellValue();
                int age = (int)row.getCell(indexMap.get("연령")).getNumericCellValue();
                int fiber = (int)row.getCell(indexMap.get("식이섬유")).getNumericCellValue();
                int protein = (int)row.getCell(indexMap.get("단백질")).getNumericCellValue();
                int calcium = (int)row.getCell(indexMap.get("칼슘")).getNumericCellValue();
                int sodium = (int)row.getCell(indexMap.get("나트륨")).getNumericCellValue();
                int potassium = (int)row.getCell(indexMap.get("칼륨")).getNumericCellValue();
                int magnesium = (int)row.getCell(indexMap.get("마그네슘")).getNumericCellValue();
                int carbohydrate = (int)row.getCell(indexMap.get("탄수화물")).getNumericCellValue();
                int fat = (int)row.getCell(indexMap.get("지방")).getNumericCellValue();

                Criteria criteria = criteriaService.create(gender, age, fiber, protein,
                        calcium, sodium, potassium, magnesium, carbohydrate, fat);

            }

            workbook.close(); // 메모리 해제
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
