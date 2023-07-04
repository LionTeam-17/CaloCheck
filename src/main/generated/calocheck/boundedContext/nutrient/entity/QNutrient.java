package calocheck.boundedContext.nutrient.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNutrient is a Querydsl query type for Nutrient
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNutrient extends EntityPathBase<Nutrient> {

    private static final long serialVersionUID = -2072770528L;

    public static final QNutrient nutrient = new QNutrient("nutrient");

    public final calocheck.base.entity.QBaseEntity _super = new calocheck.base.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final StringPath name = createString("name");

    public final StringPath unit = createString("unit");

    public final NumberPath<Double> value = createNumber("value", Double.class);

    public QNutrient(String variable) {
        super(Nutrient.class, forVariable(variable));
    }

    public QNutrient(Path<? extends Nutrient> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNutrient(PathMetadata metadata) {
        super(Nutrient.class, metadata);
    }

}

