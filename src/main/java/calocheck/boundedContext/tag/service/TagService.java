package calocheck.boundedContext.tag.service;

import calocheck.boundedContext.tag.entity.Tag;
import calocheck.boundedContext.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public void createTag(String tagName, String tagColor, int tagCriteria){

        //중복 생성 방지
        if(findByTagName(tagName).isPresent()){
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append("#");
        sb.append(tagColor);

        String tagColorCode = sb.toString();

        Tag tag = Tag.builder()
                .tagName(tagName)
                .tagColorCode(tagColorCode)
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

}
