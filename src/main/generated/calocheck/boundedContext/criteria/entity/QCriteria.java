package calocheck.boundedContext.criteria.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCriteria is a Querydsl query type for Criteria
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCriteria extends EntityPathBase<Criteria> {

    private static final long serialVersionUID = -85895408L;

    public static final QCriteria criteria = new QCriteria("criteria");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final NumberPath<Integer> calcium = createNumber("calcium", Integer.class);

    public final NumberPath<Integer> carbohydrate = createNumber("carbohydrate", Integer.class);

    public final NumberPath<Integer> fat = createNumber("fat", Integer.class);

    public final NumberPath<Integer> fiber = createNumber("fiber", Integer.class);

    public final StringPath gender = createString("gender");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> kcal = createNumber("kcal", Integer.class);

    public final NumberPath<Integer> magnesium = createNumber("magnesium", Integer.class);

    public final NumberPath<Integer> potassium = createNumber("potassium", Integer.class);

    public final NumberPath<Integer> protein = createNumber("protein", Integer.class);

    public final NumberPath<Integer> sodium = createNumber("sodium", Integer.class);

    public QCriteria(String variable) {
        super(Criteria.class, forVariable(variable));
    }

    public QCriteria(Path<? extends Criteria> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCriteria(PathMetadata metadata) {
        super(Criteria.class, metadata);
    }

}

