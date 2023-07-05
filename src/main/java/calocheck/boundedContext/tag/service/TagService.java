package calocheck.boundedContext.tag.service;

import calocheck.boundedContext.tag.entity.Tag;
import calocheck.boundedContext.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public Tag createTag(String tagName, String tagColor, int tagCriteria){

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

        return tag;
    }

}
