package calocheck.boundedContext.tracking.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTracking is a Querydsl query type for Tracking
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTracking extends EntityPathBase<Tracking> {

    private static final long serialVersionUID = 1906845120L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTracking tracking = new QTracking("tracking");

    public final calocheck.base.entity.QBaseEntity _super = new calocheck.base.entity.QBaseEntity(this);

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final NumberPath<Double> bmi = createNumber("bmi", Double.class);

    public final NumberPath<Double> bodyFat = createNumber("bodyFat", Double.class);

    public final NumberPath<Double> bodyFatPercentage = createNumber("bodyFatPercentage", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final DatePath<java.time.LocalDate> dateTime = createDate("dateTime", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    public final NumberPath<Double> height = createNumber("height", Double.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final calocheck.boundedContext.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final NumberPath<Double> muscleMass = createNumber("muscleMass", Double.class);

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public QTracking(String variable) {
        this(Tracking.class, forVariable(variable), INITS);
    }

    public QTracking(Path<? extends Tracking> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTracking(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTracking(PathMetadata metadata, PathInits inits) {
        this(Tracking.class, metadata, inits);
    }

    public QTracking(Class<? extends Tracking> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new calocheck.boundedContext.member.entity.QMember(forProperty("member")) : null;
    }

}

