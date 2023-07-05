package calocheck.boundedContext.cartFoodInfo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCartFoodInfo is a Querydsl query type for CartFoodInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCartFoodInfo extends EntityPathBase<CartFoodInfo> {

    private static final long serialVersionUID = 1027823530L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCartFoodInfo cartFoodInfo = new QCartFoodInfo("cartFoodInfo");

    public final calocheck.base.entity.QBaseEntity _super = new calocheck.base.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    public final calocheck.boundedContext.foodInfo.entity.QFoodInfo foodInfo;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final calocheck.boundedContext.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final NumberPath<Long> quantity = createNumber("quantity", Long.class);

    public QCartFoodInfo(String variable) {
        this(CartFoodInfo.class, forVariable(variable), INITS);
    }

    public QCartFoodInfo(Path<? extends CartFoodInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCartFoodInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCartFoodInfo(PathMetadata metadata, PathInits inits) {
        this(CartFoodInfo.class, metadata, inits);
    }

    public QCartFoodInfo(Class<? extends CartFoodInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.foodInfo = inits.isInitialized("foodInfo") ? new calocheck.boundedContext.foodInfo.entity.QFoodInfo(forProperty("foodInfo"), inits.get("foodInfo")) : null;
        this.member = inits.isInitialized("member") ? new calocheck.boundedContext.member.entity.QMember(forProperty("member")) : null;
    }

}

