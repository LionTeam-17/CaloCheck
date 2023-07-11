package calocheck.boundedContext.criteria.config;


import calocheck.base.util.s3.service.S3Service;
import calocheck.boundedContext.criteria.entity.Criteria;
import calocheck.boundedContext.criteria.service.CriteriaService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CriteriaDataExtractor {
    private final S3Service s3Service;

    private final CriteriaService criteriaService;

    @Bean
    public ApplicationRunner readFile() {
        return new ApplicationRunner() {
            @Override
            @Transactional
            public void run(ApplicationArguments args) throws Exception {
                if(criteriaService.findAll().isEmpty()){
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

                    String filePath = "criteria/nutrientCriteria.xlsx";

                    InputStream inputStream = s3Service.getFileFromS3(filePath);

                    Workbook workbook = null;

                    try {
                        workbook = WorkbookFactory.create(inputStream);
                        Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트를 가져옴

                        for (int i = 1; i < sheet.getLastRowNum(); i++) {
                            Row row = sheet.getRow(i);

                            String gender = row.getCell(indexMap.get("성별")).getStringCellValue();
                            int age = (int)row.getCell(indexMap.get("연령")).getNumericCellValue();
                            double carbohydrate = row.getCell(indexMap.get("탄수화물")).getNumericCellValue();
                            double fiber = row.getCell(indexMap.get("식이섬유")).getNumericCellValue();
                            double fat = row.getCell(indexMap.get("지방")).getNumericCellValue();
                            double protein = row.getCell(indexMap.get("단백질")).getNumericCellValue();
                            double calcium = row.getCell(indexMap.get("칼슘")).getNumericCellValue();
                            double sodium = row.getCell(indexMap.get("나트륨")).getNumericCellValue();
                            double potassium = row.getCell(indexMap.get("칼륨")).getNumericCellValue();
                            double magnesium = row.getCell(indexMap.get("마그네슘")).getNumericCellValue();

                            Criteria criteria = criteriaService.create(gender, age, carbohydrate, fiber,
                                    fat, protein, calcium, sodium, potassium, magnesium);

                        }
                        workbook.close(); // 메모리 해제
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}
