package calocheck.base.initData;

import calocheck.base.util.FoodDataExtractor;
import calocheck.boundedContext.comment.entity.Comment;
import calocheck.boundedContext.comment.service.CommentService;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.service.MemberService;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.post.service.PostService;
import calocheck.boundedContext.recommend.config.RecommendConfig;
import calocheck.boundedContext.recommend.service.RecommendService;
import calocheck.boundedContext.tracking.service.TrackingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Random;
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
            TrackingService trackingService
    ) {
        return args -> {
            Member[] members = IntStream
                    .rangeClosed(1, 10)
                    .mapToObj(i -> memberService.join("user%d".formatted(i), "1234", null,
                                    "닉네임%d".formatted(i), 25, 178.4, 65.0, 30.0, 20.0)
                            .getData())
                    .toArray(Member[]::new);

            Post[] posts = IntStream
                    .rangeClosed(1, 100)
                    .mapToObj(i -> postService.savePost("%d번 글입니다.".formatted(i), "%d번 내용입니다.".formatted(i), members[i % 10])
                            .getData())
                    .toArray(Post[]::new);

            recommendService.createRecommend("carbohydrate", RecommendConfig.getCarbohydrateDescription(), RecommendConfig.getCalciumFoodList());
            recommendService.createRecommend("protein", RecommendConfig.getProteinDescription(), RecommendConfig.getProteinFoodList());
            recommendService.createRecommend("fat", RecommendConfig.getFatDescription(), RecommendConfig.getFatFoodList());
            recommendService.createRecommend("calcium", RecommendConfig.getCalciumDescription(), RecommendConfig.getCalciumFoodList());
            recommendService.createRecommend("sodium", RecommendConfig.getSodiumDescription(), RecommendConfig.getSodiumFoodList());
            recommendService.createRecommend("potassium", RecommendConfig.getPotassiumDescription(), RecommendConfig.getPotassiumFoodList());
            recommendService.createRecommend("vitaminA", RecommendConfig.getVitaminADescription(), RecommendConfig.getVitaminAFoodList());
            recommendService.createRecommend("vitaminC", RecommendConfig.getVitaminCDescription(), RecommendConfig.getVitaminCFoodList());


            Comment[] comments = IntStream
                    .rangeClosed(1, 5)
                    .mapToObj(i -> commentService.saveComment("%d번 댓글입니다.".formatted(i), posts[99], members[i])
                            .getData())
                    .toArray(Comment[]::new);

            foodDataExtractor.readFile();

            //Tracking 샘플 데이터
            LocalDate startDate = LocalDate.now().minusDays(90);
            Random random = new Random();

            for (Member member : members) {
                int age = member.getAge();
                double height = member.getHeight();
                double weight = 60 + random.nextDouble() * 5;
                double bodyFat = 20 + random.nextDouble() * 5;
                double muscleMass = 30 + random.nextDouble() * 5;
                LocalDate date = startDate;

                while (!date.isAfter(LocalDate.now())) {
                    weight = Math.round((weight + (random.nextDouble() * 6) - 3) * 10) / 10.0;
                    bodyFat = Math.round((bodyFat + (random.nextDouble() * 0.2) - 0.1) * 10) / 10.0;
                    muscleMass = Math.round((muscleMass + (random.nextDouble() * 0.3) - 0.15) * 10) / 10.0;
                    trackingService.createTracking(member, date, age, height, weight, bodyFat, muscleMass);
                    date = date.plusDays(random.nextInt(4) + 1);
                }
            }

        };

    }

}