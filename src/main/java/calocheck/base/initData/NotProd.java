package calocheck.base.initData;

import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.service.MemberService;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.post.service.PostService;
import calocheck.boundedContext.recommend.config.RecommendConfig;
import calocheck.boundedContext.recommend.service.RecommendService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;


@Configuration
@Profile({"dev", "test"})
public class NotProd {
    @Bean
    @Transactional
    public CommandLineRunner initData(
            MemberService memberService,
            PostService postService,
            RecommendService recommendService
    ) {
        return args -> {
            Member[] members = IntStream
                    .rangeClosed(1, 10)
                    .mapToObj(i -> memberService.join("user%d".formatted(i), "1234", null,
                                    "닉네임%d".formatted(i), null, null, null, null, null)
                            .getData())
                    .toArray(Member[]::new);

            Post[] posts = IntStream
                    .rangeClosed(1, 100)
                    .mapToObj(i -> postService.savePost("%d번 글입니다.".formatted(i), "%d번 내용입니다.".formatted(i), members[i % 10])
                            .getData())
                    .toArray(Post[]::new);

            recommendService.createRecommend("carbohydrate", RecommendConfig.getCarbohydrateDescription(), RecommendConfig.getCalciumFoodList());


        };
    }
}