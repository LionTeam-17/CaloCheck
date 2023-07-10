package calocheck.base.initData;

import calocheck.base.util.CriteriaDataExtractor;
import calocheck.base.util.excel.service.ExcelService;
import calocheck.boundedContext.cartFoodInfo.service.CartFoodInfoService;
import calocheck.boundedContext.comment.entity.Comment;
import calocheck.boundedContext.comment.service.CommentService;
import calocheck.boundedContext.foodInfo.service.FoodInfoService;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.service.MemberService;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.tag.config.TagConfig;
import calocheck.boundedContext.tag.service.TagService;
import calocheck.boundedContext.tracking.entity.Tracking;
import calocheck.boundedContext.post.service.PostService;
import calocheck.boundedContext.postLike.entity.PostLike;
import calocheck.boundedContext.postLike.service.PostLikeService;
import calocheck.boundedContext.recommend.service.RecommendService;
import calocheck.boundedContext.tracking.service.TrackingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
            ExcelService excelService,
            TrackingService trackingService,
            PostLikeService postLikeService,
            FoodInfoService foodInfoService,
            CartFoodInfoService cartFoodInfoService,
            CriteriaDataExtractor criteriaDataExtractor,
            TagService tagService
    ) {
        return args -> {
            int MEMBER_SIZE = 6;
            int POST_SIZE = 6;

            Member[] members = IntStream
                    .rangeClosed(1, MEMBER_SIZE)
                    .filter(i -> memberService.findById((long)i).orElse(null) == null)
                    .mapToObj(i -> memberService.join("user%d".formatted(i), "1234", "male", null,
                                    "닉네임%d".formatted(i), 25, 178.4, 65.0, 30.0, 20.0)
                            .getData())
                    .toArray(Member[]::new);

            Post[] posts = IntStream
                    .rangeClosed(1, POST_SIZE)
                    .filter(i -> postService.findById((long)i).orElse(null) == null)
                    .mapToObj(i -> postService.savePost("%d번 글입니다.".formatted(i), "%d번 내용입니다.".formatted(i), members[i % MEMBER_SIZE])
                            .getData())
                    .toArray(Post[]::new);

            excelService.processExcel();

//            int COMMENT_SIZE = 5;
//
//            Comment[] comments = IntStream
//                    .rangeClosed(1, COMMENT_SIZE)
//                    .filter(i -> commentService.findById((long)i).orElse(null) == null)
//                    .mapToObj(i -> commentService.saveComment("%d번 댓글입니다.".formatted(i), posts[0], members[i])
//                            .getData())
//                    .toArray(Comment[]::new);
//
//            PostLike[] postLikes100 = IntStream
//                    .rangeClosed(0, 4)
//                    .mapToObj(i -> postLikeService.savePostLike(posts[1].getId(), members[i])
//                            .getData())
//                    .toArray(PostLike[]::new);
//
//            PostLike[] postLikes99 = IntStream
//                    .rangeClosed(0, 3)
//                    .mapToObj(i -> postLikeService.savePostLike(posts[2].getId(), members[i])
//                            .getData())
//                    .toArray(PostLike[]::new);
//            PostLike[] postLikes98 = IntStream
//                    .rangeClosed(0, 2)
//                    .mapToObj(i -> postLikeService.savePostLike(posts[3].getId(), members[i])
//                            .getData())
//                    .toArray(PostLike[]::new);
//
//            //Tracking 샘플 데이터
//            LocalDate startDate = LocalDate.now().minusDays(90);
//            Random random = new Random();
//
//            for (Member member : members) {
//                int age = member.getAge();
//                double height = member.getHeight();
//                double weight = 60 + random.nextDouble() * 5;
//                double bodyFat = 20 + random.nextDouble() * 5;
//                double muscleMass = 30 + random.nextDouble() * 5;
//                LocalDate date = startDate;
//
//                while (!date.isAfter(LocalDate.now())) {
//                    weight = Math.round((weight + (random.nextDouble() * 6) - 3) * 10) / 10.0;
//                    bodyFat = Math.round((bodyFat + (random.nextDouble() * 0.2) - 0.1) * 10) / 10.0;
//                    muscleMass = Math.round((muscleMass + (random.nextDouble() * 0.3) - 0.15) * 10) / 10.0;
//                    Tracking tracking = trackingService.createTracking(member, date, age, height, weight, bodyFat, muscleMass, null, null);
//                    trackingService.calculateBMI(tracking);
//                    trackingService.calculateBodyFatPercentage(tracking);
//                    date = date.plusDays(random.nextInt(4) + 1);
//                }
//            }

            tagService.createTag("탄수화물", TagConfig.getCarbohydrateColor(), TagConfig.getCarbohydrateCriteria());
            tagService.createTag("단백질", TagConfig.getProteinColor(), TagConfig.getProteinCriteria());
            tagService.createTag("지방", TagConfig.getFatColor(), TagConfig.getFatCriteria());
            tagService.createTag("칼슘", TagConfig.getCalciumColor(), TagConfig.getCalciumCriteria());
            tagService.createTag("나트륨", TagConfig.getSodiumColor(), TagConfig.getSodiumCriteria());
            tagService.createTag("칼륨", TagConfig.getPotassiumColor(), TagConfig.getPotassiumCriteria());
            tagService.createTag("마그네슘", TagConfig.getMagnesiumColor(), TagConfig.getMagnesiumCriteria());

            criteriaDataExtractor.readFile();
        };

    }

}