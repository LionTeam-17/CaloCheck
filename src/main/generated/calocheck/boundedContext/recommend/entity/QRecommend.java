package calocheck.boundedContext.recommend.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRecommend is a Querydsl query type for Recommend
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecommend extends EntityPathBase<Recommend> {

    private static final long serialVersionUID = -61310856L;

    public static final QRecommend recommend = new QRecommend("recommend");

    public final StringPath description = createString("description");

    public final ArrayPath<String[], String> foodList = createArray("foodList", String[].class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nutritionName = createString("nutritionName");

    public QRecommend(String variable) {
        super(Recommend.class, forVariable(variable));
    }

    public QRecommend(Path<? extends Recommend> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecommend(PathMetadata metadata) {
        super(Recommend.class, metadata);
    }

}
