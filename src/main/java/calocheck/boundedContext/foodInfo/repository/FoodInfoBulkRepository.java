package calocheck.boundedContext.foodInfo.repository;

import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FoodInfoBulkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<FoodInfo> foodInfos) {
        String sql = "INSERT INTO food_info (" +
                "food_code," +
                " food_name," +
                " manufacturer," +
                " category," +
                " portion_size," +
                " unit," +
                " total_size," +
                " kcal," +
                "create_date" +
                ") " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                foodInfos,
                foodInfos.size(),
                (PreparedStatement ps, FoodInfo foodInfo) -> {
                    ps.setString(1, foodInfo.getFoodCode());
                    ps.setString(2, foodInfo.getFoodName());
                    ps.setString(3, foodInfo.getManufacturer());
                    ps.setString(4, foodInfo.getCategory());
                    ps.setInt(5, foodInfo.getPortionSize());
                    ps.setString(6, foodInfo.getUnit());
                    ps.setInt(7, foodInfo.getTotalSize());
                    ps.setDouble(8, foodInfo.getKcal());
                    ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
                });
    }
}
