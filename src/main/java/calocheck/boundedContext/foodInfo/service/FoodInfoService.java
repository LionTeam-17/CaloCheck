package calocheck.boundedContext.foodInfo.service;

import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.foodInfo.repository.FoodInfoRepository;
import calocheck.boundedContext.nutrient.entity.Nutrient;
import calocheck.boundedContext.tag.entity.Tag;
import calocheck.boundedContext.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodInfoService {

    private final FoodInfoRepository foodInfoRepository;
    private final TagService tagService;

    @Transactional
    public FoodInfo create(FoodInfo foodInfo) {
        return foodInfoRepository.save(foodInfo);
    }

    @Transactional
    public FoodInfo create(String foodName,
                           String manufacturer,
                           String category,
                           int portionSize,
                           String unit,
                           int totalSize,
                           double kcal,
                           List<Nutrient> nutrientList) {

        FoodInfo foodInfo = FoodInfo.builder()
                .foodName(foodName)
                .manufacturer(manufacturer)
                .category(category)
                .portionSize(portionSize)
                .unit(unit)
                .totalSize(totalSize)
                .kcal(kcal)
                .nutrientList(nutrientList)
                .build();

        return foodInfoRepository.save(foodInfo);
    }

    @Transactional
    public FoodInfo update(FoodInfo foodInfo,
                           String foodName,
                           String manufacturer,
                           String category,
                           int portionSize,
                           String unit,
                           int totalSize,
                           double kcal,
                           List<Nutrient> nutrientList) {

        FoodInfo updated = foodInfo.toBuilder()
                .foodName(foodName)
                .manufacturer(manufacturer)
                .category(category)
                .portionSize(portionSize)
                .unit(unit)
                .totalSize(totalSize)
                .kcal(kcal)
                .nutrientList(nutrientList)
                .build();

        return foodInfoRepository.save(updated);
    }

    @Transactional
    public void delete(FoodInfo foodInfo) {
        foodInfoRepository.delete(foodInfo);
    }

    public FoodInfo findById(Long id) {
        Optional<FoodInfo> foodInfo = foodInfoRepository.findById(id);

        return foodInfo.orElse(null);
    }

    public List<FoodInfo> findAll() {
        return foodInfoRepository.findAll();
    }

    public Page<FoodInfo> findAll(Pageable pageable) {
        return foodInfoRepository.findAll(pageable);
    }

    public Page<FoodInfo> findByFoodNameContains(Pageable pageable, String foodName) {
        return foodInfoRepository.findByFoodNameContains(foodName, pageable);
    }

    public FoodInfo findByFoodCode(String foodCode) {
        return foodInfoRepository.findByFoodCode(foodCode).orElse(null);
    }

    public FoodInfo findByFoodName(String foodName) {
        return foodInfoRepository.findByFoodName(foodName).orElse(null);
    }

    public List<List<String>> findTop5ByFoodNameContains(String[] foodNameArr) {

        List<List<String>> top5Lists = new ArrayList<>();

        for (String foodName : foodNameArr) {

            List<FoodInfo> top5ByFoodNameContains = foodInfoRepository.findTop5ByFoodNameContains(foodName);

            List<String> top5FoodNames = new ArrayList<>();

            for (FoodInfo top5ByFoodNameContain : top5ByFoodNameContains) {

                top5FoodNames.add(top5ByFoodNameContain.getFoodName());

            }
            top5Lists.add(top5FoodNames);
        }

        return top5Lists;
    }

    public List<Tag> getTagList(FoodInfo foodInfo) {
        List<Nutrient> nutrientList = foodInfo.getNutrientList();
        List<Tag> tagList = tagService.findAllTag();

        List<Tag> returnTagList = nutrientList.stream()
                .flatMap(nutrient -> tagList.stream()
                        .filter(tag -> nutrient.getName().equals(tag.getTagName()))
                        .filter(tag -> nutrient.getValue() >= tag.getTagCriteria()))
                .collect(Collectors.toList());

        return returnTagList;
    }

    @Transactional
    public void updatePhotoUrl(FoodInfo foodInfo, String photoUrl) {

        FoodInfo updatedFoodInfo = foodInfo.toBuilder()
                .photoUrl(photoUrl)
                .build();

        foodInfoRepository.save(updatedFoodInfo);
    }
}
