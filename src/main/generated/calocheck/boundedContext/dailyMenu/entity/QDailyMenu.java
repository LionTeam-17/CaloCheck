package calocheck.boundedContext.dailyMenu.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDailyMenu is a Querydsl query type for DailyMenu
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDailyMenu extends EntityPathBase<DailyMenu> {

    private static final long serialVersionUID = 564001784L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDailyMenu dailyMenu = new QDailyMenu("dailyMenu");

    public final calocheck.base.entity.QBaseEntity _super = new calocheck.base.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    public final calocheck.boundedContext.foodInfo.entity.QFoodInfo foodInfo;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final calocheck.boundedContext.mealHistory.entity.QMealHistory mealHistory;

    public final StringPath mealType = createString("mealType");

    public final calocheck.boundedContext.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final NumberPath<Double> quantity = createNumber("quantity", Double.class);

    public QDailyMenu(String variable) {
        this(DailyMenu.class, forVariable(variable), INITS);
    }

    public QDailyMenu(Path<? extends DailyMenu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDailyMenu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDailyMenu(PathMetadata metadata, PathInits inits) {
        this(DailyMenu.class, metadata, inits);
    }

    public QDailyMenu(Class<? extends DailyMenu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.foodInfo = inits.isInitialized("foodInfo") ? new calocheck.boundedContext.foodInfo.entity.QFoodInfo(forProperty("foodInfo"), inits.get("foodInfo")) : null;
        this.mealHistory = inits.isInitialized("mealHistory") ? new calocheck.boundedContext.mealHistory.entity.QMealHistory(forProperty("mealHistory"), inits.get("mealHistory")) : null;
        this.member = inits.isInitialized("member") ? new calocheck.boundedContext.member.entity.QMember(forProperty("member")) : null;
    }

}

