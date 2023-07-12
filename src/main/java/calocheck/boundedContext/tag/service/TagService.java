package calocheck.boundedContext.tag.service;

import calocheck.boundedContext.foodInfo.entity.FoodInfo;
import calocheck.boundedContext.nutrient.entity.Nutrient;
import calocheck.boundedContext.tag.entity.Tag;
import calocheck.boundedContext.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public void createTag(String tagName, String tagColor, double tagCriteria){

        //중복 생성 방지
        if(findByTagName(tagName).isPresent()){
            return;
        }

        Tag tag = Tag.builder()
                .tagName(tagName)
                .tagColorCode("#" + tagColor)
                .tagCriteria(tagCriteria)
                .build();

        tagRepository.save(tag);
    }

    public List<Tag> findAllTag(){
        return tagRepository.findAll();
    }

    public Optional<Tag> findByTagName(String tagName){
        return tagRepository.findByTagName(tagName);
    }

    public List<Tag> getTagList(FoodInfo foodInfo) {
        List<Nutrient> nutrientList = foodInfo.getNutrientList();

        List<Tag> tagList = findAllTag();

        List<Tag> returnTagList = nutrientList.stream()
                .flatMap(nutrient -> tagList.stream()
                        .filter(tag -> nutrient.getName().equals(tag.getTagName()))
                        .filter(tag -> nutrient.getValue() >= tag.getTagCriteria()))
                .collect(Collectors.toList());

        return returnTagList;
    }

}
