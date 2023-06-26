package calocheck.boundedContext.dailyMenu.service;

import calocheck.boundedContext.dailyFoodInfo.entity.DailyFoodInfo;
import calocheck.boundedContext.dailyMenu.entity.DailyMenu;
import calocheck.boundedContext.dailyMenu.repository.DailyMenuRepository;
import calocheck.boundedContext.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyMenuService {
    private final DailyMenuRepository dailyMenuRepository;

    public DailyMenu create(Member member, List<DailyFoodInfo> dailyFoodInfos) {
        DailyMenu dailyMenu = DailyMenu.builder()
                .member(member)
                .dailyFoodInfoList(dailyFoodInfos)
                .build();

        return dailyMenuRepository.save(dailyMenu);
    }

    public DailyMenu udpate(DailyMenu dailyMenu, Member member, List<DailyFoodInfo> dailyFoodInfos) {
        DailyMenu updated = dailyMenu.toBuilder()
                .member(member)
                .dailyFoodInfoList(dailyFoodInfos)
                .build();

        return dailyMenuRepository.save(updated);
    }

    public void delete(DailyMenu dailyMenu) {
        dailyMenuRepository.delete(dailyMenu);
    }

    public DailyMenu findById(Long id) {
        Optional<DailyMenu> dailyMenu = dailyMenuRepository.findById(id);

        return dailyMenu.orElse(null);
    }
}
