package calocheck.boundedContext.mealHistory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMealHistory is a Querydsl query type for MealHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMealHistory extends EntityPathBase<MealHistory> {

    private static final long serialVersionUID = 1255429656L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMealHistory mealHistory = new QMealHistory("mealHistory");

    public final DatePath<java.time.LocalDate> createDate = createDate("createDate", java.time.LocalDate.class);

    public final ListPath<calocheck.boundedContext.dailyMenu.entity.DailyMenu, calocheck.boundedContext.dailyMenu.entity.QDailyMenu> dailyMenuList = this.<calocheck.boundedContext.dailyMenu.entity.DailyMenu, calocheck.boundedContext.dailyMenu.entity.QDailyMenu>createList("dailyMenuList", calocheck.boundedContext.dailyMenu.entity.DailyMenu.class, calocheck.boundedContext.dailyMenu.entity.QDailyMenu.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath mealMemo = createString("mealMemo");

    public final NumberPath<Integer> mealScore = createNumber("mealScore", Integer.class);

    public final StringPath mealType = createString("mealType");

    public final calocheck.boundedContext.member.entity.QMember member;

    public final ListPath<calocheck.boundedContext.nutrient.entity.Nutrient, calocheck.boundedContext.nutrient.entity.QNutrient> nutrients = this.<calocheck.boundedContext.nutrient.entity.Nutrient, calocheck.boundedContext.nutrient.entity.QNutrient>createList("nutrients", calocheck.boundedContext.nutrient.entity.Nutrient.class, calocheck.boundedContext.nutrient.entity.QNutrient.class, PathInits.DIRECT2);

    public QMealHistory(String variable) {
        this(MealHistory.class, forVariable(variable), INITS);
    }

    public QMealHistory(Path<? extends MealHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMealHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMealHistory(PathMetadata metadata, PathInits inits) {
        this(MealHistory.class, metadata, inits);
    }

    public QMealHistory(Class<? extends MealHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new calocheck.boundedContext.member.entity.QMember(forProperty("member")) : null;
    }

}

