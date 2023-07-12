package calocheck.base.initData;

import calocheck.base.util.excel.service.ExcelService;
import calocheck.boundedContext.cartFoodInfo.service.CartFoodInfoService;
import calocheck.boundedContext.comment.entity.Comment;
import calocheck.boundedContext.comment.service.CommentService;
import calocheck.boundedContext.foodInfo.service.FoodInfoService;
import calocheck.boundedContext.member.entity.Member;
import calocheck.boundedContext.member.service.MemberService;
import calocheck.boundedContext.post.entity.Post;
import calocheck.boundedContext.post.service.PostService;
import calocheck.boundedContext.postLike.entity.PostLike;
import calocheck.boundedContext.postLike.service.PostLikeService;
import calocheck.boundedContext.tracking.entity.Tracking;
import calocheck.boundedContext.tracking.service.TrackingService;
import org.hibernate.annotations.ManyToAny;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Random;
import java.util.stream.IntStream;

@Configuration
@Profile({"dev", "prod", "test"})
public class NotProd {
    @Bean
    public ApplicationRunner initData(
            MemberService memberService,
            PostService postService,
            CommentService commentService,
            ExcelService excelService,
            TrackingService trackingService,
            PostLikeService postLikeService,
            FoodInfoService foodInfoService,
            CartFoodInfoService cartFoodInfoService
    ) {
        return new ApplicationRunner() {
            @Override
            @Transactional
            public void run(ApplicationArguments args) throws Exception {
                int MEMBER_SIZE = 6;
                int POST_SIZE = 6;

                Member[] members = IntStream
                        .rangeClosed(1, MEMBER_SIZE)
                        .filter(i -> memberService.findById((long)i).orElse(null) == null)
                        .mapToObj(i -> memberService.join("user%d".formatted(i), "1234", "M", null,
                                        "닉네임%d".formatted(i), 25, 178.4, 65.0, 30.0, 20.0)
                                .getData())
                        .toArray(Member[]::new);

                Post[] posts = IntStream
                        .rangeClosed(1, POST_SIZE)
                        .filter(i -> postService.findById((long) i).orElse(null) == null)
                        .mapToObj(i -> postService.savePost("%d번 글입니다.".formatted(i), "%d번 내용입니다.".formatted(i), "A", members[i % MEMBER_SIZE])
                                .getData())
                        .toArray(Post[]::new);

            int COMMENT_SIZE = 5;

            Comment[] comments = IntStream
                    .rangeClosed(1, COMMENT_SIZE)
                    .filter(i -> commentService.findById((long) i).orElse(null) == null)
                    .mapToObj(i -> commentService.saveComment("%d번 댓글입니다.".formatted(i), postService.findById(1L).get(), memberService.findById((long) i).get())
                            .getData())
                    .toArray(Comment[]::new);

            int POST_LIKE_SIZE = 4;

            PostLike[] postLikes100 = IntStream
                    .rangeClosed(1, POST_LIKE_SIZE)
                    .filter(i -> postLikeService.findById((long) i).orElse(null) == null)
                    .mapToObj(i -> postLikeService.savePostLike(1L, memberService.findById((long) i).get())
                            .getData())
                    .toArray(PostLike[]::new);

            PostLike[] postLikes99 = IntStream
                    .rangeClosed(1, POST_LIKE_SIZE - 1)
                    .filter(i -> postLikeService.findById((long) i + 4).orElse(null) == null)
                    .mapToObj(i -> postLikeService.savePostLike(2L, memberService.findById((long) i).get())
                            .getData())
                    .toArray(PostLike[]::new);
            PostLike[] postLikes98 = IntStream
                    .rangeClosed(1, POST_LIKE_SIZE - 2)
                    .filter(i -> postLikeService.findById((long) i + 7).orElse(null) == null)
                    .mapToObj(i -> postLikeService.savePostLike(3L, memberService.findById((long) i).get())
                            .getData())
                    .toArray(PostLike[]::new);

            //Tracking 샘플 데이터
            LocalDate startDate = LocalDate.now().minusDays(90);
            Random random = new Random();

                //Tracking 샘플 데이터
                Member member1 = memberService.findById(1L).orElse(null);
                Member member2 = memberService.findById(1L).orElse(null);

                generateTracking(trackingService, member1);
                generateTracking(trackingService, member2);
            }
        };
    }

    public void generateTracking(TrackingService trackingService, Member member) {
        LocalDate startDate = LocalDate.now().minusDays(30);
        Random random = new Random();

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
            Tracking tracking = trackingService.createTracking(member, date, age, height, weight, bodyFat, muscleMass, null, null);
            trackingService.calculateBMI(tracking);
            trackingService.calculateBodyFatPercentage(tracking);
            date = date.plusDays(random.nextInt(4) + 1);
        }
    }
}