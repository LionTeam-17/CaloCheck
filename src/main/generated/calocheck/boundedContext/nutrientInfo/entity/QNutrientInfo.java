package calocheck.boundedContext.nutrientInfo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNutrientInfo is a Querydsl query type for NutrientInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNutrientInfo extends EntityPathBase<NutrientInfo> {

    private static final long serialVersionUID = 827697340L;

    public static final QNutrientInfo nutrientInfo = new QNutrientInfo("nutrientInfo");

    public final calocheck.base.entity.QBaseEntity _super = new calocheck.base.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deleteDate = _super.deleteDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Double> kcal = createNumber("kcal", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final ListPath<calocheck.boundedContext.nutrient.entity.Nutrient, calocheck.boundedContext.nutrient.entity.QNutrient> nutrientList = this.<calocheck.boundedContext.nutrient.entity.Nutrient, calocheck.boundedContext.nutrient.entity.QNutrient>createList("nutrientList", calocheck.boundedContext.nutrient.entity.Nutrient.class, calocheck.boundedContext.nutrient.entity.QNutrient.class, PathInits.DIRECT2);

    public QNutrientInfo(String variable) {
        super(NutrientInfo.class, forVariable(variable));
    }

    public QNutrientInfo(Path<? extends NutrientInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNutrientInfo(PathMetadata metadata) {
        super(NutrientInfo.class, metadata);
    }

}

