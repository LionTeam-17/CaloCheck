package calocheck.base.initData;

import calocheck.base.util.FoodDataExtractor;
import calocheck.boundedContext.comment.entity.Comment;
import calocheck.boundedContext.comment.service.CommentService;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.service.MemberService;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.post.service.PostService;
import calocheck.boundedContext.postLike.entity.PostLike;
import calocheck.boundedContext.postLike.service.PostLikeService;
import calocheck.boundedContext.recommend.config.RecommendConfig;
import calocheck.boundedContext.recommend.service.RecommendService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;


@Configuration
@Profile({"dev", "test"})
public class NotProd {
    @Bean
    @Transactional
    public CommandLineRunner initData(
            MemberService memberService,
            PostService postService,
            RecommendService recommendService,
            CommentService commentService,
            FoodDataExtractor foodDataExtractor,
            PostLikeService postLikeService
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

            recommendService.createRecommend("탄수화물", RecommendConfig.getCarbohydrateDescription(), RecommendConfig.getCalciumFoodList());
            recommendService.createRecommend("단백질", RecommendConfig.getProteinDescription(), RecommendConfig.getProteinFoodList());
            recommendService.createRecommend("지방", RecommendConfig.getFatDescription(), RecommendConfig.getFatFoodList());
            recommendService.createRecommend("칼슘", RecommendConfig.getCalciumDescription(), RecommendConfig.getCalciumFoodList());
            recommendService.createRecommend("나트륨", RecommendConfig.getSodiumDescription(), RecommendConfig.getSodiumFoodList());
            recommendService.createRecommend("칼륨", RecommendConfig.getPotassiumDescription(), RecommendConfig.getPotassiumFoodList());
            recommendService.createRecommend("비타민A", RecommendConfig.getVitaminADescription(), RecommendConfig.getVitaminAFoodList());
            recommendService.createRecommend("비타민C", RecommendConfig.getVitaminCDescription(), RecommendConfig.getVitaminCFoodList());
            recommendService.createRecommend("고단백&저지방 육류", RecommendConfig.getMeatDescription(), RecommendConfig.getMeatFoodList());
            recommendService.createRecommend("GI지수 높은 음식", RecommendConfig.getHighGIDescription(), RecommendConfig.getHighGIFoodList());
            recommendService.createRecommend("GI지수 낮은 음식", RecommendConfig.getLowGIDescription(), RecommendConfig.getLowGIFoodList());


            Comment[] comments = IntStream
                    .rangeClosed(1, 5)
                    .mapToObj(i -> commentService.saveComment("%d번 댓글입니다.".formatted(i), posts[99], members[i])
                            .getData())
                    .toArray(Comment[]::new);

            PostLike[] postLikes100 = IntStream
                    .rangeClosed(0, 4)
                    .mapToObj(i -> postLikeService.savePostLike(posts[97].getId(), members[i])
                            .getData())
                    .toArray(PostLike[]::new);
            PostLike[] postLikes99 = IntStream
                    .rangeClosed(0, 3)
                    .mapToObj(i -> postLikeService.savePostLike(posts[98].getId(), members[i])
                            .getData())
                    .toArray(PostLike[]::new);
            PostLike[] postLikes98 = IntStream
                    .rangeClosed(0, 2)
                    .mapToObj(i -> postLikeService.savePostLike(posts[99].getId(), members[i])
                            .getData())
                    .toArray(PostLike[]::new);

            foodDataExtractor.readFile();
        };
    }
}