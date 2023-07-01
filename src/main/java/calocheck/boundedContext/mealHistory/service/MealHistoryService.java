package calocheck.boundedContext.mealHistory.service;

import calocheck.boundedContext.mealHistory.repository.MealHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MealHistoryService {
    private final MealHistoryRepository dailyMenuRepository;

//    public DailyMenu create(Member member, List<DailyFoodInfo> dailyFoodInfos) {
//        DailyMenu dailyMenu = DailyMenu.builder()
//                .member(member)
//                .dailyFoodInfoList(dailyFoodInfos)
//                .build();
//
//        return dailyMenuRepository.save(dailyMenu);
//    }
//
//    public DailyMenu udpate(DailyMenu dailyMenu, Member member, List<DailyFoodInfo> dailyFoodInfos) {
//        DailyMenu updated = dailyMenu.toBuilder()
//                .member(member)
//                .dailyFoodInfoList(dailyFoodInfos)
//                .build();
//
//        return dailyMenuRepository.save(updated);
//    }
//
//    public void delete(DailyMenu dailyMenu) {
//        dailyMenuRepository.delete(dailyMenu);
//    }
//
//    public DailyMenu findById(Long id) {
//        Optional<DailyMenu> dailyMenu = dailyMenuRepository.findById(id);
//
//        return dailyMenu.orElse(null);
//    }
//
//    public DailyMenu getByMember(Member member){
//
//        return dailyMenuRepository.findByMember(member).orElse(null);
//    }
}
