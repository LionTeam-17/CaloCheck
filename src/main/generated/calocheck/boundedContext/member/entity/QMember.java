package calocheck.boundedContext.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 833850310L;

    public static final QMember member = new QMember("member1");

    public final calocheck.base.entity.QBaseEntity _super = new calocheck.base.entity.QBaseEntity(this);

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final NumberPath<Double> bmr = createNumber("bmr", Double.class);

    public final NumberPath<Double> bodyFat = createNumber("bodyFat", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    public final StringPath email = createString("email");

    public final StringPath gender = createString("gender");

    public final NumberPath<Double> height = createNumber("height", Double.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final NumberPath<Double> muscleMass = createNumber("muscleMass", Double.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath providerTypeCode = createString("providerTypeCode");

    public final StringPath username = createString("username");

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

