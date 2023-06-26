package calocheck.boundedContext.dailyFoodInfo.service;

import calocheck.boundedContext.dailyFoodInfo.entity.DailyFoodInfo;
import calocheck.boundedContext.dailyFoodInfo.repository.DailyFoodInfoRepository;
import calocheck.boundedContext.dailyMenu.entity.DailyMenu;
import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyFoodInfoService {
    private final DailyFoodInfoRepository dailyFoodInfoRepository;

    public DailyFoodInfo create(DailyMenu dailyMenu, FoodInfo foodInfo) {
        DailyFoodInfo dailyFoodInfo = DailyFoodInfo.builder()
                .dailyMenu(dailyMenu)
                .foodInfo(foodInfo)
                .build();

        return dailyFoodInfoRepository.save(dailyFoodInfo);
    }

    public DailyFoodInfo udpate(DailyFoodInfo dailyFoodInfo, DailyMenu dailyMenu, FoodInfo foodInfo) {
        DailyFoodInfo updated = dailyFoodInfo.toBuilder()
                .dailyMenu(dailyMenu)
                .foodInfo(foodInfo)
                .build();

        return dailyFoodInfoRepository.save(updated);
    }

    public void delete(DailyFoodInfo dailyFoodInfo) {
        dailyFoodInfoRepository.delete(dailyFoodInfo);
    }

    public DailyFoodInfo findById(Long id) {
        Optional<DailyFoodInfo> dailyFoodInfo = dailyFoodInfoRepository.findById(id);

        return dailyFoodInfo.orElse(null);
    }
}
