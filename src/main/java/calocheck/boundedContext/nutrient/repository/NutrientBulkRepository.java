package calocheck.boundedContext.nutrient.repository;

import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.nutrient.entity.Nutrient;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NutrientBulkRepository {
    private final JdbcTemplate jdbcTemplate;

    public void saveAll(List<Nutrient> nutrients) {
        String sql = "INSERT INTO nutrient (" +
                "food_info_id," +
                "`name`," +
                "value" +
                "VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                nutrients,
                nutrients.size(),
                (PreparedStatement ps, Nutrient nutrient) -> {
                    ps.setLong(1, nutrient.getFoodInfo().getId());
                    ps.setString(2, nutrient.getName());
                    ps.setDouble(3, nutrient.getValue());
                });

    }
}
