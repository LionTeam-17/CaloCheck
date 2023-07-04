package calocheck.boundedContext.foodInfo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFoodInfo is a Querydsl query type for FoodInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFoodInfo extends EntityPathBase<FoodInfo> {

    private static final long serialVersionUID = -1621398870L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFoodInfo foodInfo = new QFoodInfo("foodInfo");

    public final calocheck.base.entity.QBaseEntity _super = new calocheck.base.entity.QBaseEntity(this);

    public final StringPath category = createString("category");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    public final StringPath foodName = createString("foodName");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath manufacturer = createString("manufacturer");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final calocheck.boundedContext.nutrientInfo.entity.QNutrientInfo nutrientInfo;

    public final NumberPath<Integer> portionSize = createNumber("portionSize", Integer.class);

    public final NumberPath<Integer> totalSize = createNumber("totalSize", Integer.class);

    public final StringPath unit = createString("unit");

    public QFoodInfo(String variable) {
        this(FoodInfo.class, forVariable(variable), INITS);
    }

    public QFoodInfo(Path<? extends FoodInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFoodInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFoodInfo(PathMetadata metadata, PathInits inits) {
        this(FoodInfo.class, metadata, inits);
    }

    public QFoodInfo(Class<? extends FoodInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.nutrientInfo = inits.isInitialized("nutrientInfo") ? new calocheck.boundedContext.nutrientInfo.entity.QNutrientInfo(forProperty("nutrientInfo")) : null;
    }

}

